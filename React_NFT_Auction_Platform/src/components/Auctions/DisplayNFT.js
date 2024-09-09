import React, { Component } from 'react';
import Web3 from 'web3';
import NFTManagerABI from './NFTManagerABI.json'; // Import your contract ABI

class DisplayNFT extends Component {
    state = {
        tokenURI: '',
        tokenId: 1, // Assuming you want to display the first minted token
        imgSrc: '',
    };

    async componentDidMount() {
        await this.loadBlockchainData();
    }

    async loadBlockchainData() {
        const web3 = new Web3(new Web3.providers.HttpProvider("https://sepolia.infura.io/v3/b2e950fb6dfb4ece9d7b84ea0cce4c93"));
        const networkId = await web3.eth.net.getId();
        const NFTManagerAddress = '0x6960Dbc55282Ac60da31277E3233c8f3c2b6c764'; // Replace with your deployed contract address
        const nftManager = new web3.eth.Contract(NFTManagerABI, NFTManagerAddress);

        const tokenURI = await nftManager.methods.tokenURI(this.state.tokenId).call();
        this.setState({ tokenURI });

        // Assuming the tokenURI is an IPFS link or a URL to a JSON file containing metadata
        const response = await fetch(`https://ipfs.io/ipfs/${tokenURI}`);
        const metadata = await response.json();
        this.setState({ imgSrc: metadata.image });
    }

    render() {
        return (
            <div className="card">
                <div className="image-over">
                    <img className="card-img-top" src={this.state.imgSrc} alt="NFT" />
                </div>
                <div className="card-caption col-12 p-0">
                    <div className="card-body">
                        <h5 className="mb-0">Minted NFT #{this.state.tokenId}</h5>
                        <a href={`https://ipfs.io/ipfs/${this.state.tokenURI}`} target="_blank" rel="noopener noreferrer">
                            View Metadata
                        </a>
                    </div>
                </div>
            </div>
        );
    }
}

export default DisplayNFT;
