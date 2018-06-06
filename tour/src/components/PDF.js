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
        pageNum: null,
    }

    doc = null

    constructor(props) {
        super(props)
        const {url} = props

        pdfjsLib.getDocument(url).then(doc => {
            this.doc = doc
            this.setState({
                pages: doc.pdfInfo.numPages,
            })
        })
    }

    render() {
        const {pages, pageNum} = this.state

        return <div className="pdf-viewer">
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
