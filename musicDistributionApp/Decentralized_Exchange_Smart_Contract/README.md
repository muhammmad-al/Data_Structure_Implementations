# DEX Smart Contract

This project implements a Decentralized Exchange (DEX) smart contract in Solidity. The DEX allows users to provide liquidity, exchange Ether (ETH) for an ERC20 token, and vice versa.

## Description

The `DEX` contract includes the following features:

- **Liquidity Management**: Users can add or remove liquidity from the DEX pool.
- **Token Exchange**: Users can exchange ETH for an ERC20 token and the other way around.
- **Fee Collection**: The DEX collects fees on exchanges and allows the deployer to collect these fees.
- **Price Oracle Integration**: Integrates with a price oracle to retrieve the price of ETH.

### Contract Functions

- **createPool**: Initializes the liquidity pool with ETH and ERC20 tokens.
- **addLiquidity**: Adds ETH and corresponding ERC20 tokens to the liquidity pool.
- **removeLiquidity**: Removes liquidity (ETH and ERC20 tokens) from the pool.
- **receive**: Facilitates the exchange of ETH for ERC20 tokens.
- **onERC20Received**: Facilitates the exchange of ERC20 tokens for ETH.
- **getEtherPrice**: Retrieves the current price of ETH from the price oracle.
- **getTokenPrice**: Derives the price of the ERC20 token based on the price of ETH.
- **getPoolLiquidityInUSDCents**: Returns the total value of the liquidity pool in USD cents.
- **setEtherPricer**: Sets the address of the price oracle contract.
- **getDEXinfo**: Provides detailed information about the DEX.
- **reset**: Function that reverts (per assignment instructions).
- **supportsInterface**: Checks if the contract supports a given interface.

## Usage

To deploy and interact with this contract, you will need:

- Solidity compiler version `^0.8.24`.
- A development environment such as Remix, Truffle, or Hardhat.
- The `IERC20.sol`, `IDEX.sol`, `IERC165.sol`, `IEtherPriceOracle.sol`, `IERC20Receiver.sol`, and `IERC20Metadata.sol` interface files.

### Example Deployment

Deploy the contract in a Solidity-compatible environment with the required dependencies
