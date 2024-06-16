// SPDX-License-Identifier: MIT
//Name: Muhammad Al-Atrash, Computing ID: hnb2at
pragma solidity ^0.8.24;

import "./ERC721.sol";
import "./INFTManager.sol";

contract NFTManager is INFTManager, ERC721 {
    uint256 private _tokenCount;
    mapping(uint256 => string) private _tokenURIs;
    mapping(string => bool) private _usedURIs;
    string private _baseTokenURI;

    constructor(string memory name, string memory symbol) ERC721(name, symbol) {
        _baseTokenURI = "https://andromeda.cs.virginia.edu/ccc/ipfs/files/";
    }

    function count() external view override returns (uint) {
        return _tokenCount;
    }

    function mintWithURI(address _to, string memory _uri) public override returns (uint) {
        require(!_usedURIs[_uri], "URI already used");
        
        _usedURIs[_uri] = true; 
        _tokenCount++;
        _mint(_to, _tokenCount);
        _setTokenURI(_tokenCount, _uri);
        return _tokenCount;
    }

    function _setTokenURI(uint256 tokenId, string memory _uri) internal {
        _tokenURIs[tokenId] = _uri;
    }

    function mintWithURI(string memory _uri) external override returns(uint256) {
        return mintWithURI(msg.sender, _uri);
    }

    function supportsInterface(bytes4 interfaceId) public view virtual override(ERC721, IERC165) returns (bool) {
        return interfaceId == type(INFTManager).interfaceId || super.supportsInterface(interfaceId);
    }

    function tokenURI(uint256 tokenId) public view virtual override(ERC721, IERC721Metadata) returns (string memory) {
        require(ownerOf(tokenId) != address(0), "ERC721Metadata: URI query for nonexistent token");

        string memory _tokenURI = _tokenURIs[tokenId];
        string memory base = _baseURI();

        if (bytes(base).length == 0) {
            return _tokenURI;
        }

   
        if (bytes(_tokenURI).length > 0) {
            return string.concat(base, _tokenURI);
        }

        return base;
    }
}
