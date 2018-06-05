import React from 'react'
import {render} from 'react-dom'
import classNames from 'classnames'
import './style.scss'
import content from './content.json'
import {Map} from 'immutable'

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
        const {children} = this.props

        return <div className="content">
            <p>{children}</p>
        </div>
    }
}

class UI extends React.Component {
    state = {
        active: null,
    }

    render() {
        const {content, active} = this.props

        return <div className="tour">
            <Menu>
                {content.keySeq().map(key =>
                    <Link active={active == key} key={key} children={key} onClick={e => {
                        e.preventDefault()
                        this.setState({active: key})
                    }} />)}
            </Menu>
            <Content>
                {active != null &&
                    content.get(active)}
            </Content>
        </div>
    }
}

document.addEventListener('DOMContentLoaded', e => {
    const mount = document.createElement('div')
    render(<UI content={Map(content)} />, mount)
    document.querySelector('body').appendChild(mount)
})