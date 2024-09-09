import React from 'react';
import { useHistory } from 'react-router-dom';
import Web3 from 'web3';

const Header = () => {
    const history = useHistory(); // Use the useHistory hook

    // Function to connect to MetaMask
    const connectWallet = async () => {
        if (window.ethereum) {
            try {
                const web3 = new Web3(window.ethereum);
                // Request account access if needed
                await window.ethereum.request({ method: 'eth_requestAccounts' });
                // Get the user's account
                const accounts = await web3.eth.getAccounts();
                console.log('Connected account:', accounts[0]);
                alert(`Connected account: ${accounts[0]}`);
                // You can now interact with the user's wallet
            } catch (error) {
                console.error('User denied account access', error);
            }
        } else {
            // MetaMask is not installed
            alert('MetaMask is not installed. Please install MetaMask to use this feature.');
        }
    };

    // Function to handle the Live Auctions click
    const handleLiveAuctionsClick = () => {
        history.push('/explore-1'); // Redirect to the explore-1 page
    };

    return (
        <header id="header">
            {/* Navbar */}
            <nav data-aos="zoom-out" data-aos-delay={800} className="navbar navbar-expand">
                <div className="container header">
                    <div className="ml-auto" />
                    {/* Navbar */}
                    <ul className="navbar-nav items mx-auto">
                        <li className="nav-item dropdown">
                            <a className="nav-link" href="/">Home</a>
                        </li>
                        <li className="nav-item dropdown">
                            <a className="nav-link" href="#" onClick={handleLiveAuctionsClick}>Live Auctions</a>
                        </li>
                        <li className="nav-item">
                            <a href="/create" className="nav-link">Mint an NFT</a>
                        </li>
                        <li className="nav-item dropdown">
                            <a href="/activity" className="nav-link">Activity</a>
                        </li>
                        <li className="nav-item">
                            <a href="/contact" className="nav-link">Contact</a>
                        </li>
                    </ul>
                    {/* Navbar Icons */}
                    <ul className="navbar-nav icons">
                        <li className="nav-item">
                            <a href="#" className="nav-link" data-toggle="modal" data-target="#search">
                                <i className="fas fa-search" />
                            </a>
                        </li>
                    </ul>
                    {/* Navbar Toggler */}
                    <ul className="navbar-nav toggle">
                        <li className="nav-item">
                            <a href="#" className="nav-link" data-toggle="modal" data-target="#menu">
                                <i className="fas fa-bars toggle-icon m-0" />
                            </a>
                        </li>
                    </ul>
                    {/* Navbar Action Button */}
                    <ul className="navbar-nav action">
                        <li className="nav-item ml-3">
                            <a href="#" className="btn ml-lg-auto btn-bordered-white" onClick={connectWallet}>
                                <i className="icon-wallet mr-md-2" />Wallet Connect
                            </a>
                        </li>
                    </ul>
                </div>
            </nav>
        </header>
    );
};

export default Header;
