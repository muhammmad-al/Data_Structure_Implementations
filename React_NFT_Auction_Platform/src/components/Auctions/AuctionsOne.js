import React, { Component } from 'react';

const initData = {
    pre_heading: "Auctions",
    heading: "Live Auctions",
    btnText: "View All"
}

const data = [
    {
        id: "1",
        img: "/img/WrongDecision.png",
        date: "2021-12-09",
        title: "Wrong Decision",
        seller_thumb: "/img/Platinum.webp",
        seller: "@QueenP",
        price: "1.5 ETH",
        count: "1 of 1"
    },
    {
        id: "2",
        img: "/img/FATE.png",
        date: "2021-10-05",
        title: "FATE",
        seller_thumb: "/img/Extinguish.webp",
        seller: "@ALLE",
        price: "2.7 ETH",
        count: "1 of 1"
    },
    {
        id: "3",
        img: "/img/Twisted.webp",
        date: "2021-09-15",
        title: "Twisted",
        seller_thumb: "/img/Haunted.png",
        seller: "@ASPblots",
        price: "2.3 ETH",
        count: "1 of 1"
    },
    {
        id: "4",
        img: "/img/Twenty.jpg",
        date: "2021-12-29",
        title: "Twenty",
        seller_thumb: "/img/ObxArham.webp",
        seller: "@ObxArham",
        price: "1.8 ETH",
        count: "1 of 1"
    },
    {
        id: "5",
        img: "/img/Slowly.png",
        date: "2022-01-24",
        title: "Slowly",
        seller_thumb: "/img/chiefCommander.png",
        seller: "@ChiefCommander",
        price: "1.7 ETH",
        count: "1 of 1"
    },
    {
        id: "6",
        img: "/img/WITCHES.png",
        date: "2022-03-30",
        title: "WITCHES",
        seller_thumb: "/img/42.png",
        seller: "@42",
        price: "3.5 ETH",
        count: "1 of 1"
    },
    {
        id: "7",
        img: "/img/NERO.png",
        date: "2022-04-01",
        title: "Nero",
        seller_thumb: "/img/ObxArham.webp",
        seller: "@ObxArham",
        price: "2.1 ETH",
        count: "1 of 1"
    },
    {
        id: "8",
        img: "/img/Eight.png",
        date: "2022-04-10",
        title: "Eight",
        seller_thumb: "/img/ObxArham.webp",
        seller: "@ObxArham",
        price: "2.9 ETH",
        count: "1 of 1"
    },
    {
        id: "9",
        img: "/img/Kingdom.png",
        date: "2022-04-10",
        title: "Kingdom",
        seller_thumb: "/img/42.png",
        seller: "@42",
        price: "3.3 ETH",
        count: "1 of 1"
    },
    {
        id: "10",
        img: "/img/Napoleon.png",
        date: "2022-04-10",
        title: "Napoleon",
        seller_thumb: "/img/Haunted.png",
        seller: "@ASPblots",
        price: "2.1 ETH",
        count: "1 of 1"
    },
    {
        id: "11",
        img: "/img/Rave.png",
        date: "2022-04-10",
        title: "Rave",
        seller_thumb: "/img/Extinguish.webp",
        seller: "@ALLE",
        price: "5.2 ETH",
        count: "1 of 1"
    },

]

class AuctionsOne extends Component {
    state = {
        initData: {},
        data: []
    }
    componentDidMount(){
        this.setState({
            initData: initData,
            data: data
        })
    }
    render() {
        return (
            <section className="live-auctions-area">
                <div className="container">
                    <div className="row">
                        <div className="col-12">
                            {/* Intro */}
                            <div className="intro d-flex justify-content-between align-items-end m-0">
                                <div className="intro-content">
                                    <span>{this.state.initData.pre_heading}</span>
                                    <h3 className="mt-3 mb-0">{this.state.initData.heading}</h3>
                                </div>
                                <div className="intro-btn">
                                    <a className="btn content-btn" href="/explore-1">{this.state.initData.btnText}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div className="auctions-slides">
                        <div className="swiper-container slider-mid items">
                            <div className="swiper-wrapper">
                                {/* Single Slide */}
                                {this.state.data.map((item, idx) => {
                                    return (
                                        <div key={`auc_${idx}`} className="swiper-slide item">
                                            <div className="card">
                                                <div className="image-over">
                                                    {/* Removed the <a> tag */}
                                                    <img className="card-img-top" src={item.img} alt="" />
                                                </div>
                                                {/* Card Caption */}
                                                <div className="card-caption col-12 p-0">
                                                    {/* Card Body */}
                                                    <div className="card-body">
                                                        <div className="countdown-times mb-3">
                                                            <div className="countdown d-flex justify-content-center" data-date={item.date} />
                                                        </div>
                                                        {/* Removed the <a> tag */}
                                                        <h5 className="mb-0">{item.title}</h5>
                                                        <div className="seller d-flex align-items-center my-3">
                                                            {/* Removed the <a> tag */}
                                                            <img className="avatar-sm rounded-circle" src={item.seller_thumb} alt="" />
                                                            <span className="ml-2">{item.seller}</span>
                                                        </div>
                                                        <div className="card-bottom d-flex justify-content-between">
                                                            <span>{item.price}</span>
                                                            <span>{item.count}</span>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    );
                                })}
                            </div>
                            <div className="swiper-pagination" />
                        </div>
                    </div>
                </div>
            </section>
        );
    }
}

export default AuctionsOne;