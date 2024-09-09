import React, { Component } from 'react';

class Activity extends Component {
    state = {
        data: {
            preHeading: "Activity",
            heading: "Latest Bids",
            tabTitle_1: "All",
            tabTitle_2: "Following",
            tabTitle_3: "New",
            widgetTitle: "Filter by Tags"
        },
        tabData_1: [
            {
                img: "https://ipfs.io/ipfs/QmbDXbcoM4X8Xbdwf11nnnJQTVyqTxE2Ty9Ys47Ke9R7ZS",
                title: "Kingdom",
                price: "3.3 ETH",
                time: "10 minutes ago",
                seller: "42"
            },
            {
                img: "https://ipfs.io/ipfs/QmRqT4TdMCzBuW7AdKch86H717T9Vpj7rqJJz2MZSoKGdV",
                title: "Fate",
                price: "5.2 ETH",
                time: "20 minutes ago",
                seller: "ALLE"
            }
        ],
        tabData_2: [
            {
                img: "https://ipfs.io/ipfs/QmaDtWv9XToerpE6Y8av79cVJbZzhfHvXJSKmL8Vqa7FxQ",
                title: "Nero",
                price: "2.1 ETH",
                time: "5 minutes ago",
                seller: "ObxArham"
            }
        ],
        tabData_3: [
            {
                img: "https://ipfs.io/ipfs/QmXaAneEfLcnooU9PygpEZZu8GC7zkFPBy8kzaWpxPmDDa",
                title: "Napoleon",
                price: "2.1 ETH",
                time: "2 hours ago",
                seller: "ASPblots"
            }
        ],
        filterData: [
            { title: "Art" },
            { title: "Music" },
            { title: "Domain Names" },
            { title: "Virtual Worlds" },
            { title: "Trading Cards" }
        ]
    }

    render() {
        return (
            <section className="activity-area load-more">
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
                        <div className="col-12 col-md-6 col-lg-8">
                            {/* Netstorm Tab */}
                            <ul className="netstorm-tab nav nav-tabs" id="nav-tab">
                                <li>
                                    <a className="active" id="nav-home-tab" data-toggle="pill" href="#nav-home">
                                        <h5 className="m-0">{this.state.data.tabTitle_1}</h5>
                                    </a>
                                </li>
                                <li>
                                    <a id="nav-profile-tab" data-toggle="pill" href="#nav-profile">
                                        <h5 className="m-0">{this.state.data.tabTitle_2}</h5>
                                    </a>
                                </li>
                                <li>
                                    <a id="nav-contact-tab" data-toggle="pill" href="#nav-contact">
                                        <h5 className="m-0">{this.state.data.tabTitle_3}</h5>
                                    </a>
                                </li>
                            </ul>
                            {/* Tab Content */}
                            <div className="tab-content" id="nav-tabContent">
                                <div className="tab-pane fade show active" id="nav-home">
                                    <ul className="list-unstyled">
                                        {/* Single Tab List */}
                                        {this.state.tabData_1.map((item, idx) => {
                                            return (
                                                <li key={`ato_${idx}`} className="single-tab-list d-flex align-items-center">
                                                    {/* Removed the <a> tag */}
                                                    <img className="avatar-lg" src={item.img} alt="" />
                                                    {/* Activity Content */}
                                                    <div className="activity-content ml-4">
                                                        {/* Removed the <a> tag */}
                                                        <h5 className="mt-0 mb-2">{item.title}</h5>
                                                        <p className="m-0">Bid listed for <strong>{item.price}</strong> {item.time} <br />by {item.seller}</p>
                                                    </div>
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                                <div className="tab-pane fade" id="nav-profile">
                                    <ul className="list-unstyled">
                                        {/* Single Tab List */}
                                        {this.state.tabData_2.map((item, idx) => {
                                            return (
                                                <li key={`att_${idx}`} className="single-tab-list d-flex align-items-center">
                                                    {/* Removed the <a> tag */}
                                                    <img className="avatar-lg" src={item.img} alt="" />
                                                    {/* Activity Content */}
                                                    <div className="activity-content ml-4">
                                                        {/* Removed the <a> tag */}
                                                        <h5 className="mt-0 mb-2">{item.title}</h5>
                                                        <p className="m-0">Bid listed for <strong>{item.price}</strong> {item.time} <br />by {item.seller}</p>
                                                    </div>
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                                <div className="tab-pane fade" id="nav-contact">
                                    <ul className="list-unstyled">
                                        {/* Single Tab List */}
                                        {this.state.tabData_3.map((item, idx) => {
                                            return (
                                                <li key={`atth_${idx}`} className="single-tab-list d-flex align-items-center">
                                                    {/* Removed the <a> tag */}
                                                    <img className="avatar-lg" src={item.img} alt="" />
                                                    {/* Activity Content */}
                                                    <div className="activity-content ml-4">
                                                        {/* Removed the <a> tag */}
                                                        <h5 className="mt-0 mb-2">{item.title}</h5>
                                                        <p className="m-0">Bid listed for <strong>{item.price}</strong> {item.time} <br />by {item.seller}</p>
                                                    </div>
                                                </li>
                                            );
                                        })}
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div className="col-12 col-md-6 col-lg-4">
                            {/* Activity Content */}
                            <div className="activity-content mt-5 mt-lg-0">
                                {/* Single Widget */}
                                <div className="single-widget">
                                    {/* Search Widget */}
                                    <div className="widget-content search-widget">
                                        <form action="#">
                                            <input type="text" placeholder="Enter your keywords" />
                                        </form>
                                    </div>
                                </div>
                                {/* Single Widget */}
                                <div className="single-widget">
                                    {/* Filter Widget */}
                                    <div className="widget filter-widget">
                                        <h4 className="title">{this.state.data.widgetTitle}</h4>
                                        {/* Filter Widget Content */}
                                        <div className="widget-content">
                                            {/* Tags Widget Items */}
                                            <div className="widget-content filter-widget-items mt-3">
                                                {this.state.filterData.map((item, idx) => {
                                                    return (
                                                        <a key={`fd_${idx}`} href="#" className="badge tag">{item.title}</a>
                                                    );
                                                })}
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

export default Activity;
