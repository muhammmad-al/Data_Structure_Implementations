import React, { Component } from 'react';

class Work extends Component {
    state = {
        data: {
            "preHeading": "How It Works",
            "heading": "Create, mint, and auction NFTs",
            "workData": [
                {
                    "id": 1,
                    "icon": "icons icon-wallet text-effect",
                    "title": "Set up your wallet",
                    "text": "Connect your MetaMask cryptocurrency wallet to the platform by clicking the Wallet Connect button in the top right corner. As this is a demo marketplace, please ensure you are connected to the Sepolia testnet" 
                },
                {
                    "id": 2,
                    "icon": "icons icon-grid text-effect",
                    "title": "Mint your NFTs",
                    "text": "Click Create and easily mint your digital creations as NFTs directly on the blockchain. The entire minting process is decentralized, ensuring you have full control over your assets"
                },
                {
                    "id": 3,
                    "icon": "icons icon-drawer text-effect",
                    "title": "Create your auction",
                    "text": "Click Create and easily mint your digital creations as NFTs directly on the blockchain. The entire minting process is decentralized, ensuring you have full control over your assets"
                },
                {
                    "id": 4,
                    "icon": "icons icon-bag text-effect",
                    "title": "List them for sale",
                    "text": "Choose between auctions, fixed-price listings, and declining-price listings. You choose how you want to sell your NFTs!"
                }
            ]
        },
        workData: [
            {
                "id": 1,
                "icon": "icons icon-wallet text-effect",
                "title": "Set up your wallet",
                "text": "Connect your MetaMask cryptocurrency wallet to the platform by clicking the Wallet Connect button in the top right corner."
            },
            {
                "id": 2,
                "icon": "icons icon-grid text-effect",
                "title": "Mint your NFTs",
                "text": "Leveraging the latest GPT-4o models, click Create and easily mint your digital creations as NFTs directly on the Sepolia blockchain."
            },
            {
                "id": 3,
                "icon": "icons icon-drawer text-effect",
                "title": "Create your auction",
                "text": "Launch an auction for your newly minted NFTs. This decentralized auction platform ensures transparency and fairness in all bidding processes."
            },
            {
                "id": 4,
                "icon": "icons icon-bag text-effect",
                "title": "Place bids on auctions",
                "text": "Browse live auctions and place bids on your favorite. All transactions are processed on-chain, providing you with a trustless and secure experience."
            }
        ]
    };

    render() {
        return (
            <section className="work-area">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            {/* Intro */}
                            <div className="intro mb-4">
                                <div className="intro-content">
                                    <span>{this.state.data.preHeading}</span>
                                    <h3 className="mt-3 mb-0">{this.state.data.heading}</h3>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row items">
                        {this.state.workData.map((item, idx) => {
                            return (
                                <div key={`wd_${idx}`} className="col-12 col-sm-6 col-lg-3 item">
                                    {/* Single Work */}
                                    <div className="single-work">
                                        <i className={item.icon} />
                                        <h4>{item.title}</h4>
                                        <p>{item.text}</p>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                </div>
            </section>
        );
    }
}

export default Work;
