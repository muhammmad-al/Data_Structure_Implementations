import React, { Component } from 'react';

class Footer extends Component {
    state = {
        data: {
            content: "",
            socialData: [
                {
                    link: "facebook",
                    icon: "fab fa-facebook-f"
                },
                {
                    link: "twitter",
                    icon: "fab fa-twitter"
                },
                {
                    link: "google-plus",
                    icon: "fab fa-google-plus-g"
                },
                {
                    link: "vine",
                    icon: "fab fa-vine"
                }
            ],
            widget_1: "Useful Links",
            widgetData_1: [
                { text: "All NFTs" },
                { text: "How It Works" },
                { text: "Create" },
                { text: "Explore" },
                { text: "Privacy & Terms" }
            ],
            widget_2: "Community",
            widgetData_2: [
                { text: "Help Center" },
                { text: "Partners" },
                { text: "Suggestions" },
                { text: "Blog" },
                { text: "Newsletter" }
            ],
            widget_3: "Subscribe Us"
        }
    };

    render() {
        return (
            <footer className="footer-area">
                {/* Footer Top */}
                <div className="footer-top">
                    <div className="container">
                        <div className="row">
                            <div className="col-12 col-sm-6 col-lg-3 res-margin">
                                {/* Footer Items */}
                                <div className="footer-items">
                                    {/* Disclaimer */}
                                    <p className="footer-disclaimer">{this.state.data.content}</p>
                                    {/* Social Icons */}
                                    <div className="social-icons d-flex justify-content-center">
                                        {this.state.data.socialData.map((item, idx) => (
                                            <a key={`sd_${idx}`} className={item.link} href="#">
                                                <i className={item.icon} />
                                            </a>
                                        ))}
                                    </div>
                                </div>
                            </div>
                            <div className="col-12 col-sm-6 col-lg-3 res-margin">
                                {/* Footer Items */}
                                <div className="footer-items">
                                    {/* Footer Title */}
                                    <h4 className="footer-title">{this.state.data.widget_1}</h4>
                                    <ul>
                                        {this.state.data.widgetData_1.map((item, idx) => (
                                            <li key={`wdo_${idx}`}><a href="#">{item.text}</a></li>
                                        ))}
                                    </ul>
                                </div>
                            </div>
                            <div className="col-12 col-sm-6 col-lg-3 res-margin">
                                {/* Footer Items */}
                                <div className="footer-items">
                                    {/* Footer Title */}
                                    <h4 className="footer-title">{this.state.data.widget_2}</h4>
                                    <ul>
                                        {this.state.data.widgetData_2.map((item, idx) => (
                                            <li key={`wdo_${idx}`}><a href="#">{item.text}</a></li>
                                        ))}
                                    </ul>
                                </div>
                            </div>
                            <div className="col-12 col-sm-6 col-lg-3">
                                {/* Footer Items */}
                                <div className="footer-items">
                                    {/* Footer Title */}
                                    <h4 className="footer-title">{this.state.data.widget_3}</h4>
                                    {/* Subscribe Form */}
                                    <div className="subscribe-form d-flex align-items-center">
                                        <input type="email" className="form-control" placeholder="info@yourmail.com" />
                                        <button type="submit" className="btn"><i className="icon-paper-plane" /></button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                {/* Footer Bottom */}
                <div className="footer-bottom">
                    <div className="container">
                        <div className="row">
                            <div className="col-12">
                                {/* Copyright Area */}
                                <div className="copyright-area d-flex flex-wrap justify-content-center justify-content-sm-between text-center py-4">
                                    {/* Copyright Left */}
                                    <div className="copyright-left"></div>
                                    {/* Copyright Right */}
                                    <div className="copyright-right"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </footer>
        );
    }
}

export default Footer;
