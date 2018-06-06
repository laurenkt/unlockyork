import React from 'react'

export default class extends React.Component {
    constructor(props) {
        super(props)

        this.state = {
            body: require(props.url)
        }
    }

    render() {
        return <div>{this.state.body}</div>
    }
}