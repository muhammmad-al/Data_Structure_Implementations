// SPDX-License-Identifier: MIT
// Name: Muhammad Al-Atrash, ComputingID: hnb2at
pragma solidity ^0.8.24;

import "./ERC20.sol";
import "./ITokenCC.sol";

contract TokenCC is ITokenCC, ERC20 {
    constructor() ERC20("MadAl", "MAL") {
        _mint(msg.sender, 1000000 * 10**uint256(decimals()));
    }

    function requestFunds() external pure override {
        revert("Per assignment description");
    }

    function decimals() public view virtual override (ERC20, IERC20Metadata) returns (uint8) {
        return 11;
    }

    function supportsInterface(bytes4 interfaceId) public view virtual override(IERC165) returns (bool) {
        return interfaceId == type(IERC165).interfaceId
            || interfaceId == type(IERC20).interfaceId
            || interfaceId == type(IERC20Metadata).interfaceId
            || interfaceId == type(ITokenCC).interfaceId;
    }
}
