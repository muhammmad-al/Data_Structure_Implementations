# Custom Cryptocurrency Implementation

This project implements a simple cryptocurrency management system using Python. It allows generating wallets, funding wallets, transferring funds, and verifying transactions.

## Description

The script supports the following commands:

- **name**: Display the name of the cryptocurrency.
- **generate**: Generate a new wallet.
- **address**: Get the wallet tag from a wallet file.
- **fund**: Fund a wallet.
- **transfer**: Transfer funds between wallets.
- **verify**: Verify a transaction.
- **balance**: Check the balance of a wallet.
- **genesis**: Generate the genesis block of the blockchain.
- **mine**: Add a block to the blockchain.
- **validate**: Verify hashes in the blockchain.

## Usage

To use the script, run the following command with the desired command and arguments:

```bash
python cryptocurrency.py <command> [arguments]

### Examples

- Generate a new wallet:
  ```bash
  python cryptocurrency.py generate my_wallet.txt
