import React from 'react';

const WalletConnect = () => {
    const connectWallet = async () => {
        if (typeof window.ethereum !== 'undefined') {
            try {
                await window.ethereum.request({ method: 'eth_requestAccounts '});
                const accounts = await window.ethereum.request( { method: 'eth_accounts' });
                console.log('Connected account:', accounts[0]);
                alert('Connected to account: ${accounts[0]}');
            } catch (error) {
                console.error('user denied account access or an error occured', error);
            }
        } else {
            alert('MetaMask is not installed. Please install it to use this feature')
        }
    };

    return (
        <button onClick={connectWallet}>
            Wallet Connect
        </button>
    );
};

export default WalletConnect;