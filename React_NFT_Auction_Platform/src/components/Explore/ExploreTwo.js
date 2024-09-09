import React, { Component } from 'react';

class ExploreTwo extends Component {
    state = {
        data: {
            preHeading: "Explore Local Collection",
            heading: "Discover Unique NFTs",
            btnText: "View All",
            btn_2: "Load More",
            exploreData: [
                {
                    id: "1",
                    img: "/img/WrongDecision.png",
                    title: "Wrong Decision",
                    owner: "QueenP",
                    price: "1.5 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "2",
                    img: "/img/FATE.png",
                    title: "FATE",
                    owner: "ALLE",
                    price: "2.7 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "3",
                    img: "/img/Twisted.webp",
                    title: "Twisted",
                    owner: "ASPblots",
                    price: "2.3 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "4",
                    img: "/img/Twenty.jpg",
                    title: "Twenty",
                    owner: "ObxArham",
                    price: "1.8 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "5",
                    img: "/img/Slowly.png",
                    title: "Slowly",
                    owner: "ChiefCommander",
                    price: "1.7 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "6",
                    img: "/img/WITCHES.png",
                    title: "WITCHES",
                    owner: "42",
                    price: "3.5 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "7",
                    img: "/img/NERO.png",
                    title: "Nero",
                    owner: "ObxArham",
                    price: "2.1 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "8",
                    img: "/img/Eight.png",
                    title: "Eight",
                    owner: "ObxArham",
                    price: "2.9 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "9",
                    img: "/img/Kingdom.png",
                    title: "Kingdom",
                    owner: "42",
                    price: "3.3 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "10",
                    img: "/img/Napoleon.png",
                    title: "Napoleon",
                    owner: "ASPblots",
                    price: "4.2 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                },
                {
                    id: "11",
                    img: "/img/Rave.png",
                    title: "Rave",
                    owner: "ALLE",
                    price: "5.2 ETH",
                    count: "1 of 1",
                    btnText: "Place a Bid"
                }
            ]
        }
    };

    render() {
        return (
            <section className="explore-area">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            {/* Intro */}
                            <div className="intro d-flex justify-content-between align-items-end m-0">
                                <div className="intro-content">
                                    <span>{this.state.data.preHeading}</span>
                                    <h3 className="mt-3 mb-0">{this.state.data.heading}</h3>
                                </div>
                                <div className="intro-btn">
                                    <a className="btn content-btn" href="/explore-1">{this.state.data.btnText}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="row items">
                        {this.state.data.exploreData.map((item, idx) => {
                            return (
                                <div key={`edt_${idx}`} className="col-12 col-sm-6 col-lg-3 item">
                                    <div className="card">
                                        <div className="image-over">
                                            <img className="card-img-top" src={item.img} alt={item.title} />
                                        </div>
                                        {/* Card Caption */}
                                        <div className="card-caption col-12 p-0">
                                            {/* Card Body */}
                                            <div className="card-body">
                                                <h5 className="mb-0">{item.title}</h5>
                                                <div className="seller d-flex align-items-center my-3">
                                                    <span>Owned By</span>
                                                    <h6 className="ml-2 mb-0">{item.owner}</h6>
                                                </div>
                                                <div className="card-bottom d-flex justify-content-between">
                                                    <span>{item.price}</span>
                                                    <span>{item.count}</span>
                                                </div>
                                                {/* Disabled Button */}
                                                <button className="btn btn-bordered-white btn-smaller mt-3" disabled>
                                                    <i className="icon-handbag mr-2" />{item.btnText}
                                                </button>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            );
                        })}
                    </div>
                    <div className="row">
                        <div className="col-12 text-center">
                            <a id="load-btn" className="btn btn-bordered-white mt-5" href="#">{this.state.data.btn_2}</a>
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

export default ExploreTwo;
