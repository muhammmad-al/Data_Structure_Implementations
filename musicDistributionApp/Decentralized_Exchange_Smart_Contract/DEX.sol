
// SPDX-License-Identifier: GPL-3.0-or-later

pragma solidity ^0.8.24; 

import "./IERC20.sol";
import "./IDEX.sol";
import "./IERC165.sol";
import "./IEtherPriceOracle.sol";
import "./IERC20Receiver.sol";
import "./IERC20Metadata.sol";

contract DEX is IDEX {
    bool private _poolInitialized = false; 
    bool private _adjustingLiquidity = false;
    //define state variables
    uint public override k;  //update this whenever liquidity is added or removed from the pool 
    uint public override x; //ether liquidity represented in wei (18 decimal places) 
    uint public override y; //token liquidity (11 decimal places) 
    uint public override feeNumerator; //define fraction for fees 
    uint public override feeDenominator;
    uint public override feesToken; //amount of token fees accumulated for all addresses 
    uint public override feesEther; //amount of ether fees accumulated for all addresses 
    mapping(address => uint) public override etherLiquidityForAddress; //ETH liquidity for address in pool 
    mapping(address => uint) public override tokenLiquidityForAddress; //TCC liquidity for address in pool 
    address public override etherPricer; //address of ether pricer contract being used 
    address public override ERC20Address; //address of ERC-20 token manager contract being used 

    //constructor to initialize DEX with address parameters 
    constructor() {

    }

    //call to ERC20 contract to retrieve TCC decimal value 
    function decimals() public view override returns (uint) {
        IERC20Metadata tokenContract = IERC20Metadata(ERC20Address);
        return tokenContract.decimals();
    }

    //call to ERC20 contract to retrieve TCC symbol
    function symbol() public view override returns(string memory) {
        IERC20Metadata tokenContract = IERC20Metadata(ERC20Address);
        return tokenContract.symbol(); 
    }
    
    //call to EtherPricer contract to retrieve ETH price 
    function getEtherPrice() public view override returns (uint) {
        IEtherPriceOracle pricer = IEtherPriceOracle(etherPricer);
        return pricer.price();
    }

    //derive TCC price based on ETH price 
    function getTokenPrice() public view override returns (uint) {
        uint etherPriceInCents = getEtherPrice();

        require(x > 0 && y > 0, "Insufficient liquidity in the pool");

        uint tccPriceInETH = (x / 1e7) * 1e18 / y; 
        uint tccPriceInCents = tccPriceInETH * etherPriceInCents / 1e18;

        return tccPriceInCents;
    }

    //get total DEX liquidity pool value in USD cents 
    function getPoolLiquidityInUSDCents() public view override returns (uint) {
        uint etherPriceInCents = getEtherPrice();
        uint totalEthValueInCents = x * etherPriceInCents / 1e18; 
        uint totalPoolLiquidityInUSDCents = totalEthValueInCents * 2;

        return totalPoolLiquidityInUSDCents;
    }
    
    //change the contract that sets ether price 
    function setEtherPricer(address p) public override {
        require (p != address(0), "Invalid address");
        etherPricer = p; 
    }

    //provides the following information: address of this DEX contract, TCC symbol, TCC name, ERC20 address
    //k parameter, ether liquidity, token liquidity, fee numerator, fee denominator, TCC decimals, 
    //ether fees collected, token fees collected 
    function getDEXinfo() public view override returns (address, string memory, string memory, address,
            uint, uint, uint, uint, uint, uint, uint, uint) {
        
        IERC20Metadata tokenContract = IERC20Metadata(ERC20Address);
        string memory TCCname = tokenContract.name(); 
        string memory TCCsymbol = symbol();
        uint TCCdecimals = decimals();

        return (address(this), TCCsymbol, TCCname, ERC20Address, k, x, y, feeNumerator, feeDenominator, TCCdecimals, 
                feesEther, feesToken); 
    }

    //function reverts per assignment instructions
    function reset() public pure override {
        revert("Per assignment instructions");
    }
    
    //creation of DEX liquidity pool 
    function createPool(uint _tokenAmount, uint _feeNumerator, uint _feeDenominator, 
            address _erc20token, address _etherPricer) public override payable {

        require(!_poolInitialized, "DEX liquidity pool already initialized"); 
        require(msg.value > 0, "Must provide ETH for liquidity");
        require(_tokenAmount > 0, "Must provide TCC for liquidity");
        require(_erc20token != address(0) && _etherPricer != address(0), "Invalid address");
        require(_feeNumerator <= _feeDenominator, "Invalid fee structure");

        ERC20Address = _erc20token;
        etherPricer = _etherPricer;
        feeNumerator = _feeNumerator;
        feeDenominator = _feeDenominator;

        x += msg.value; //ensure correct decimals
        y += _tokenAmount;
        k = x * y; 

        //transfer TCC from caller to DEX contract 
        require(IERC20(ERC20Address).transferFrom(msg.sender, address(this), _tokenAmount), "Token transfer failed");
    
        _poolInitialized = true; 

        emit liquidityChangeEvent();
    }

    //manage the addition of ETH and corresponding TCC to DEX liquidity pool
    function addLiquidity() public override payable {
        require(msg.value > 0, "Must send ETH to add liquidity");

        _adjustingLiquidity = true; //turn off onERC20Received processing 

        //calculate amount of TCC liquidity provider must deposit to maintain pool's ratio
        uint tokenAmount = (msg.value * y) / x;

        //check that TCC is approved and DEX can transfer TCC from provider's account
        require(IERC20(ERC20Address).allowance(msg.sender, address(this)) >= tokenAmount, "Token allowance too low");

        //transfer tokens from liquidity provider to DEX
        require(IERC20(ERC20Address).transferFrom(msg.sender, address(this), tokenAmount), "Failed to transfer TCC");

        //update DEX's liquidity
        x += msg.value;
        y += tokenAmount; 
        k = x * y; 

        _adjustingLiquidity = false; //turn onERC20Received processing back on

        emit liquidityChangeEvent();
    }

    //allow liquidity providers to remove contributions from liquidity pools 
    function removeLiquidity(uint amountWei) public override {
        //validate withdrawal amount doesn't exceed user's contribution
        require(etherLiquidityForAddress[msg.sender] >= amountWei, "Withdrawal exceeds contribution");

        _adjustingLiquidity = true; //turn off onERC20Received processing 

        //calculate proportion of pool being removed
        uint ethProportion = amountWei / x;
        uint tokenAmount = ethProportion * y; 

        //update pool's liquidity tracking
        x -= amountWei;
        y -= tokenAmount;
        k = x * y; 

        //update user's liquidity contribution tracking
        etherLiquidityForAddress[msg.sender] -= amountWei;
        tokenLiquidityForAddress[msg.sender] -= tokenAmount;

        //transfer ETH back to provider
        (bool ethTransferSuccess,) = msg.sender.call{value: amountWei}("");
        require(ethTransferSuccess, "Failed to transfer ETH");

        // Transfer TCC back to the liquidity provider
        bool tokenTransferSuccess = IERC20(ERC20Address).transfer(msg.sender, tokenAmount);
        require(tokenTransferSuccess, "Failed to transfer TCC");

        _adjustingLiquidity = false; //turn off onERC20Received processing 

        emit liquidityChangeEvent(); 
    }

    //facilitates exchange (ETH for TCC)
    receive() override payable external{
        require(msg.value > 0, "Must send ETH in exchange for TCC");

        uint fee = msg.value * feeNumerator / feeDenominator; //calculate fee
        uint effectiveETH = msg.value - fee; //amount of ETH after subtracting fee

        //calculate amount of TCC to be sen
        uint newY = k / (x + effectiveETH); 
        uint tokenAmount = y - newY; //amount of TCC to send 

        require (y >= tokenAmount, "Insufficient TCC liquidity in DEX");

        //update state of DEX
        x += effectiveETH; 
        y = newY; 
        k = x * y;

        feesEther += fee; //update collected ETH fees 

        //transfer TCC to sender
        IERC20(ERC20Address).transfer(msg.sender, tokenAmount);

        emit liquidityChangeEvent(); 
    }

    //faciliates exchange (TCC for ETH) 
    function onERC20Received(address from, uint amount, address erc20) external returns (bool) {
        require(erc20 == ERC20Address, "Unsupported token");

        //skip processing if adjusting liquidity 
        if (_adjustingLiquidity) {
            return true;
        }

        //calculate the fee
        uint fee = amount * feeNumerator / feeDenominator;
        uint effectiveTCCAmount = amount - fee;

        //ensure the token received is available for exchange
        require(y + effectiveTCCAmount <= IERC20(ERC20Address).balanceOf(address(this)), "Token balance exceeded");

        //calculate ETH to send back
        uint newY = y + effectiveTCCAmount;
        uint newX = k / newY; 
        uint ethToSend = x - newX; 

        require(ethToSend <= address(this).balance, "Insufficient ETH in DEX");

        //update state
        x = newX;
        y = newY;
        k = x * y; //should be the same as before 

        //transfer ETH to the sender
        (bool success, ) = from.call{value: ethToSend}("");
        require(success, "Failed to send ETH");

        //update fees collected
        feesToken += fee;

        emit liquidityChangeEvent();
        
        return true; 
    }

    //contract supports 3 interfaces: IERC165, IERC20Receiver, IDEX 
    function supportsInterface(bytes4 interfaceId) public view virtual override returns(bool) {
        return interfaceId == type(IERC165).interfaceId 
            || interfaceId == type(IERC20Receiver).interfaceId
            || interfaceId == type(IDEX).interfaceId;
    }

}
