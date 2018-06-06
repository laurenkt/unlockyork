import React from 'react'
import {render} from 'react-dom'
import classNames from 'classnames'
import './style.scss'
import content from './content.json'
import {OrderedMap} from 'immutable'
import pdfjsLib from 'pdfjs-dist'
import autobind from 'autobind-decorator'

function slug(name) {
    return name.toLowerCase().replace(/[\s]+/g, '-');
}

class Link extends React.PureComponent {
    render() {
        const {children, active, onClick, ...props} = this.props

        return <li {...props} className={classNames('menu-item', {active})}>
            <a onClick={onClick} className="menu-item-link" href={'#'+slug(children)}>{children}</a>
        </li>
    }
}

class Menu extends React.PureComponent {
    render() {
        const {children} = this.props

        return <ul className="menu">
            {children}
        </ul>
    }
}

class PDF extends React.Component {
    state = {
        pages: 0,
        pageNum: null,
    }

    doc = null
    canvas = null

    constructor(props) {
        super(props)
        const {url} = props

        pdfjsLib.getDocument(url).then(doc => {
            this.doc = doc
            this.setState({
                pages: doc.pdfInfo.numPages,
                pageNum: 1,
            })
        })
    }

    @autobind
    async canvasDidMount(canvas) {
        //const {pageNum} = this.state
        this.canvas = canvas

        //this.renderPage(pageNum)
    }

    @autobind
    async renderPage(pageNum) {
        const page = await this.doc.getPage(pageNum)
        const scale = 1.5
        const viewport = page.getViewport(scale)

        // Prepare canvas using PDF page dimensions
        const canvasContext = this.canvas.getContext('2d')
        this.canvas.height = viewport.height
        this.canvas.width = viewport.width

        await page.render({canvasContext, viewport})
    }

    @autobind
    next(e) {
        e.preventDefault()
        this.setState({pageNum: Math.min(this.state.pages, this.state.pageNum+1)})
    }

    @autobind
    previous(e) {
        e.preventDefault()
        this.setState({pageNum: Math.max(1, this.state.pageNum-1)})
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (this.state.pages == 0)
            return

        if (prevState.pageNum != this.state.pageNum)
            this.renderPage(this.state.pageNum)
    }

    render() {
        const {pages, pageNum} = this.state

        return <div className="pdf-viewer">
            {pages == null &&
                <p>Loading PDF</p>}
            {pages != null &&
                <div>
                    Page {pageNum} of {pages}
                    <button onClick={this.previous}>Previous</button>
                    <button onClick={this.next}>Next</button>
                    <canvas ref={this.canvasDidMount}></canvas>
                </div>}
        </div>
    }
}

class Content extends React.PureComponent {
    state ={
        canvas: null,
    }

    render() {
        const {content} = this.props;
        const {canvas} = this.state;

        let body = content || 'No content'

        if (body.match(/\.pdf$/)) {

        }

        return <div className="content">
            {body.match(/\.pdf$/) &&
                <PDF url={`./documents/${body}`} />}
        </div>
    }
}

class UI extends React.Component {
    state = {
        active: null,
    }

    componentDidUpdate() {
        const {active} = this.state;

        // Update URL
        history.replaceState({}, active || 'HTML Tour', active == null ? '' : `#${slug(active)}`);
    }

    render() {
        const {content} = this.props
        const {active} = this.state

        return <div className="tour">
            <Menu>
                {content.keySeq().map(key =>
                    <Link active={active == key} key={key} children={key} onClick={e => {
                        e.preventDefault()
                        this.setState({active: key})
                    }} />)}
            </Menu>
            <Content content={content.get(active)} />
        </div>
    }
}

document.addEventListener('DOMContentLoaded', e => {
    const mount = document.createElement('div')
    render(<UI content={OrderedMap(content)} />, mount)
    document.querySelector('body').appendChild(mount)
})