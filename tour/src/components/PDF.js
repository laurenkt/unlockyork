import React from 'react'
import autobind from 'autobind-decorator'
import {range} from 'lodash'

export default class PDF extends React.Component {

    render() {
        const {url} = this.props

        return <div className="pdf-viewer fill">
            <iframe className="pdf-file fill" src={url} />
        </div>
    }
}
