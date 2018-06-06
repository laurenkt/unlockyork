import React from 'react'
import {render} from 'react-dom'
import classNames from 'classnames'
import './style.scss'
import content from './document.js'
import {OrderedMap} from 'immutable'
import autobind from 'autobind-decorator'
import PDF from './components/PDF'
import Markdown from './components/Markdown'

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


class Content extends React.PureComponent {
    render() {
        const {content} = this.props;

        if (!content)
            return <div>No content</div>

        return <div className="content">
            {content.type && content.type == 'pdf' &&
                <PDF url={content.path} />}
            {!content.type &&
                <div dangerouslySetInnerHTML={{__html: content}} />}
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