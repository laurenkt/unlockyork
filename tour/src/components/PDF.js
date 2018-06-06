import React from 'react'
import pdfjsLib from 'pdfjs-dist'
import autobind from 'autobind-decorator'
import {range} from 'lodash'

class PDFPage extends React.Component {
    canvas = null

    @autobind
    renderCanvas(canvas) {
        this.canvas = canvas
        this.updateCanvas()
    }

    @autobind
    updateCanvas() {
        const {page} = this.props
        page.then(page => {
            if (!this.canvas) {
                return
            }

            const viewport = page.getViewport(1.2)

            // Prepare canvas using PDF page dimensions
            const canvasContext = this.canvas.getContext('2d')
            this.canvas.height = viewport.height
            this.canvas.width = viewport.width

            page.render({canvasContext, viewport})
        })
    }

    componentDidUpdate(nextProps) {
        this.updateCanvas()
    }

    render() {
        return <canvas className="pdf-page" ref={this.renderCanvas} />
    }
}

export default class PDF extends React.Component {
    state = {
        pages: 0,
    }

    doc = null

    componentDidMount() {
        const {url} = this.props

        pdfjsLib.getDocument(url).then(doc => {
            this.doc = doc
            this.setState({
                pages: doc.pdfInfo.numPages,
            })
        })
    }

    render() {
        const {url, external} = this.props
        const {pages} = this.state

        let external_type
        if (external)
            external_type = external.match(/\.([^.]+)$/)

        console.log('external', external)
        console.log('external_type', external_type)

        return <div className="pdf-viewer">
            {!external &&
                <a className="button button-pdf" target="_blank" href={url}>View PDF</a>}
            {external &&
                <a className={`button button-${external_type}`} target="_blank" href={`file://${external}`}>View {external_type[1].toUpperCase()}</a>}
            {pages == null &&
            <p>Loading PDF</p>}
            {pages != 0 &&
            <div>
                {range(0, pages).map(i =>
                      <PDFPage key={i} page={this.doc.getPage(i+1)} />)}
            </div>}
        </div>
    }
}
