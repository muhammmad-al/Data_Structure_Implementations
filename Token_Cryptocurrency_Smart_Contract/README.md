# TokenCC Solidity Contract

This project implements a Solidity smart contract called `TokenCC`. The contract is an ERC20 token with a custom name and symbol, and it includes additional functionality as specified by the `ITokenCC` interface.

## Description

The `TokenCC` contract extends the standard ERC20 implementation and includes the following features:

- **Token Name**: MadAl
- **Token Symbol**: MAL
- **Initial Supply**: 1,000,000 MAL
- **Decimals**: 11

### Contract Functions

- **Constructor**: Initializes the token with the specified name, symbol, and initial supply, minting the total supply to the contract deployer.
- **requestFunds**: A function that always reverts, per the assignment description.
- **decimals**: Returns the number of decimals the token uses (overridden to return 11).
- **supportsInterface**: Checks if the contract supports a given interface.

## Usage

To deploy and interact with this contract, you will need:

- Solidity compiler version `^0.8.24`.
- A development environment such as Remix, Truffle, or Hardhat.
- The `ERC20.sol` and `ITokenCC.sol` interface files.

### Example Deployment

Deploy the contract in a Solidity-compatible environment with the required dependencies
