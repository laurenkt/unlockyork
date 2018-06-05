import React from 'react'
import {render} from 'react-dom'
import classNames from 'classnames'
import './style.scss'
import content from './content.json'
import {Map} from 'immutable'
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
    @autobind
    async canvasDidMount(canvas) {
        const {url} = this.props

        const doc = await pdfjsLib.getDocument(url)

        const page = await doc.getPage(1 /* pageNumber */)

        var scale = 1.5;
        var viewport = page.getViewport(scale);

        // Prepare canvas using PDF page dimensions
        var context = canvas.getContext('2d');
        canvas.height = viewport.height;
        canvas.width = viewport.width;

        // Render PDF page into canvas context
        var renderContext = {
            canvasContext: context,
            viewport: viewport
        };
        var renderTask = await page.render(renderContext);
        console.log('Page rendered');
    }

    render() {
        return <canvas ref={this.canvasDidMount}></canvas>
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
    render(<UI content={Map(content)} />, mount)
    document.querySelector('body').appendChild(mount)
})