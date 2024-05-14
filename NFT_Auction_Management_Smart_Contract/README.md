# NFT Auction Management System Solidity Contract

This project implements a Solidity smart contract called `Auctioneer`. The contract manages auctions for NFTs using the `NFTManager` contract.

## Description

The `Auctioneer` contract includes the following features:

- **Manage Auctions**: Start and close auctions for NFTs.
- **Place Bids**: Allow users to place bids on active auctions.
- **Collect Fees**: Collect fees from auctions.
- **Auction Details**: Provide details and status of auctions.

### Contract Functions

- **collectFees**: Allows the deployer to collect accumulated fees.
- **startAuction**: Starts a new auction for a specified NFT.
- **closeAuction**: Closes an active auction and transfers the NFT to the highest bidder.
- **placeBid**: Places a bid on an active auction.
- **auctionTimeLeft**: Returns the time left for a specified auction.
- **nftmanager**: Returns the address of the `NFTManager` contract.
- **supportsInterface**: Checks if the contract supports a given interface.

## Usage

To deploy and interact with this contract, you will need:

- Solidity compiler version `^0.8.24`.
- A development environment such as Remix, Truffle, or Hardhat.
- The `IAuctioneer.sol`, `NFTManager.sol`, and `IERC165.sol` interface files.

### Example Deployment

Deploy the contract in a Solidity-compatible environment with the required dependencies
