import React, { Component } from 'react';

const initData = {
    pre_heading: "Auctions",
    heading: "Live Auctions",
    btnText: "View All"
}

const data = [
    
]

class AuctionsThree extends Component {
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
            <section className="live-auctions-area pt-0">
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
                                    <a className="btn content-btn" href="/auctions">{this.state.initData.btnText}</a>
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
                                                    <a href="/item-details">
                                                        <img className="card-img-top" src={item.img} alt="" />
                                                    </a>
                                                </div>
                                                {/* Card Caption */}
                                                <div className="card-caption col-12 p-0">
                                                    {/* Card Body */}
                                                    <div className="card-body">
                                                        <div className="countdown-times mb-3">
                                                            <div className="countdown d-flex justify-content-center" data-date={item.date} />
                                                        </div>
                                                        <a href="/item-details">
                                                            <h5 className="mb-0">{item.title}</h5>
                                                        </a>
                                                        <a className="seller d-flex align-items-center my-3" href="/item-details">
                                                            <img className="avatar-sm rounded-circle" src={item.seller_thumb} alt="" />
                                                            <span className="ml-2">{item.seller}</span>
                                                        </a>
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

export default AuctionsThree;