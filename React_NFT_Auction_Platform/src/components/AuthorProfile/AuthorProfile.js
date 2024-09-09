import React, { Component } from 'react';

class AuthorProfile extends Component {
    state = {
        data: {
            img: "https://ipfs.io/ipfs/QmWPyzfooiHGA1HKpjc1k2L21SMJADQCbQU58txjhAimG2",  // Replace with actual image URL
            authorImg: "https://example.com/author-thumbnail.jpg",  // Replace with actual author image URL
            author: "NFT#12: EXTINGUISH",  // Replace with actual author name
            content: "",  // Replace with actual content
            authorId: "Transaction Hash",  // Replace with actual author ID
            btnText: "Mint",  // Replace with actual button text
            socialData: [
                { link: "https://facebook.com/johndoe", icon: "fab fa-facebook-f" },
                { link: "https://twitter.com/johndoe", icon: "fab fa-twitter" },
                { link: "https://instagram.com/johndoe", icon: "fab fa-instagram" }
                // Add more social links if needed
            ]
        }
    }

    render() {
        return (
            <div className="card no-hover text-center">
                <div className="image-over">
                    <img className="card-img-top" src={this.state.data.img} alt="" />
                </div>
                {/* Card Caption */}
                <div className="card-caption col-12 p-0">
                    {/* Card Body */}
                    <div className="card-body">
                        <h5 className="mb-1 mt-2">{this.state.data.author}</h5> {/* Adjusted margins here */}
                        <p className="my-3">{this.state.data.content}</p>
                        <div className="input-group">
                            <input type="text" className="form-control" placeholder={this.state.data.authorId} />
                            <div className="input-group-append">
                                <button><i className="icon-docs" /></button>
                            </div>
                        </div>
                        {/* Social Icons */}
                        <div className="social-icons d-flex justify-content-center my-3">
                            {this.state.data.socialData.map((item, idx) => {
                                return (
                                    <a key={`asd_${idx}`} className={item.link} href="#">
                                        <i className={item.icon} />
                                        <i className={item.icon} />
                                    </a>
                                );
                            })}
                        </div>
                        <a className="btn btn-bordered-white btn-smaller" href="#">{this.state.data.btnText}</a>
                    </div>
                </div>
            </div>
        );
    }
}

export default AuthorProfile;
