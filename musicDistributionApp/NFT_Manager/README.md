# NFTManager Solidity Contract

This project implements a Solidity smart contract called `NFTManager`. The contract is an ERC721 token manager with additional functionality as specified by the `INFTManager` interface.

## Description

The `NFTManager` contract extends the standard ERC721 implementation and includes the following features:

- **Token Count**: Keeps track of the number of minted tokens.
- **Minting with URI**: Allows minting of new tokens with a unique URI.
- **Token URI Management**: Manages URIs for tokens, ensuring each URI is unique.
- **Base URI**: Sets a base URI for all token URIs.

### Contract Functions

- **count**: Returns the total number of tokens minted.
- **mintWithURI**: Mints a new token with a specified URI.
- **_setTokenURI**: Sets the URI for a specific token.
- **supportsInterface**: Checks if the contract supports a given interface.
- **tokenURI**: Returns the URI for a given token.

## Usage

To deploy and interact with this contract, you will need:

- Solidity compiler version `^0.8.24`.
- A development environment such as Remix, Truffle, or Hardhat.
- The `ERC721.sol` and `INFTManager.sol` interface files.

### Example Deployment

Deploy the contract in a Solidity-compatible environment with the required dependencies
