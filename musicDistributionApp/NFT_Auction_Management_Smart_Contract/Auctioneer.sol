
// SPDX-License-Identifier: GPL-3.0-or-later
pragma solidity ^0.8.24;

import "./IAuctioneer.sol";
import "./NFTManager.sol";
import "./IERC165.sol";

contract Auctioneer is IAuctioneer {

    address public override deployer; 
    uint public override numAuctions;
    uint public override totalFees;
    uint public override uncollectedFees;
    address public nftManagerAddress; 
    
    
    mapping (uint => Auction) public override auctions; 

    mapping (uint => bool) public nftInAuction; 
    NFTManager public nftManager;

    constructor() {
        deployer = msg.sender;
        nftManager = new NFTManager("Mad Al", "MAL");
        nftManagerAddress = address(nftManager);
    }

    function collectFees() external override {
        require(msg.sender == deployer, "Only the deployer can collect fees");
        require(uncollectedFees > 0, "No fees to collect");

        uint feesToTransfer = uncollectedFees;
        uncollectedFees = 0;

        (bool success, ) = payable(deployer).call{value: feesToTransfer}("");
        require(success, "Failed to transfer fees");
    }

    function startAuction(uint m, uint h, uint d, string memory data, uint reserve, uint nftId) external override returns (uint) {
        uint duration = m * 1 minutes + h * 1 hours + d * 1 days; 
        require (duration > 0, "Auction duration cannot be zero");
        require(bytes(data).length > 0, "Description cannot be empty");
        require(!nftInAuction[nftId], "Auction for NFT is already active");
        require(nftManager.ownerOf(nftId) == msg.sender, "Caller must be NFT owner");

        
        require(nftManager.getApproved(nftId) == address(this) || nftManager.isApprovedForAll(msg.sender, address(this)),
                "Auctioneer not approved to manage this NFT");
        
        nftManager.transferFrom(msg.sender, address(this), nftId);

        uint endTime = block.timestamp + duration;
        nftInAuction[nftId] = true;

        Auction memory newAuction = Auction({
            id: numAuctions,
            num_bids: 0, 
            data: data,
            highestBid: reserve,
            winner: address(0), 
            initiator: msg.sender, 
            nftid: nftId, 
            endTime: endTime, 
            active: true
        });

        auctions[numAuctions] = newAuction;

        emit auctionStartEvent(numAuctions);
        
        numAuctions++; 
        return numAuctions - 1;
    }
    

    function closeAuction(uint id) external override {
        Auction storage auction = auctions[id];
        require(block.timestamp > auction.endTime, "Auction has not ended yet");
        require(auction.active == true, "Auction is not active");

        nftInAuction[auction.nftid] = false; 
        auction.active = false; 

        uint fee; 
        if (auction.num_bids > 0) {
            fee = auction.highestBid / 100;
            uint amountToTransfer = auction.highestBid - fee;

            nftManager.transferFrom(address(this), auction.winner, auction.nftid);

            (bool transferToInitiatorSuccess, ) = auction.initiator.call{value : amountToTransfer}("");
            require(transferToInitiatorSuccess, "Failed to transfer funds to initiator");

            totalFees += fee;
            uncollectedFees += fee;
        } else {
            nftManager.transferFrom(address(this), auction.initiator, auction.nftid);
        }

        emit auctionCloseEvent(id);
    }

    function placeBid(uint id) payable external override {
        Auction storage auction = auctions[id];
        
        require(auction.active == true, "Auction is not active");
        require(block.timestamp < auction.endTime, "The auction has already closed");
        require(msg.value > auction.highestBid, "Your bid is not higher than the current highest bid");

        if(auction.highestBid > 0 && auction.winner != address(0)) {
            (bool refundSuccess, ) = auction.winner.call{value: auction.highestBid}("");
            require(refundSuccess, "Failed to refund the previous highest bidder");
        }

        auction.highestBid = msg.value;
        auction.winner = msg.sender;
        auction.num_bids += 1; 
        
        emit higherBidEvent(id);
    }

    function auctionTimeLeft(uint id) external view override returns (uint) {
        Auction storage auction = auctions[id]; 

        if (auction.endTime > block.timestamp) {
            return auction.endTime - block.timestamp;
        } else {
            return 0;
        }
    }

    function nftmanager() external view override returns (address) {
        return nftManagerAddress; 
    }

    function supportsInterface(bytes4 interfaceId) external pure override returns (bool) {
        return interfaceId == type(IAuctioneer).interfaceId || interfaceId == type(IERC165).interfaceId;
    }

}
