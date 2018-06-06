import React from 'react'
import {render} from 'react-dom'
import classNames from 'classnames'
import './style.scss'
import content from './document.js'
import {OrderedMap} from 'immutable'
import autobind from 'autobind-decorator'
import PDF from './components/PDF'
import {range} from 'lodash'

function slug(name) {
    return name.toLowerCase().replace(/[\s]+/g, '-');
}

class Link extends React.PureComponent {
    @autobind
    onClick(name) {
        return e => {
            e.preventDefault()
            this.props.onClick(name)
        }
    }

    render() {
        const {children, active, onClick, name, ...props} = this.props

        return <li {...props} className={classNames('menu-item', {active})}>
            <a onClick={this.onClick(name)} className="menu-item-link" href={'#'+slug(name)}>
                {name}
            </a>
            {children.map && children.length > 0 && <ul>
                {children.map(c => <a key={c.name} onClick={this.onClick(`${name}/${c.name}`)}>{c.name}</a>)}
            </ul>}
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

class ContentChildren extends React.PureComponent {
    state = {
        active: null,
    }

    render() {
        const {children} = this.props
        const {active} = this.state

        return <div>
            {range(children.length).map(idx =>
                <button key={idx} onClick={e => {
                        e.preventDefault()
                        this.setState({active: idx})
                    }
                }>{children[idx].props.content.name}</button>)}
            {active != null &&
                children[active]}
        </div>
    }
}

class ContentItem extends React.PureComponent {
    render() {
        const {content} = this.props;

        if (!content)
            return <div>No content</div>

        if (content.children) {
            return <ContentChildren>
                {content.children.map((c, idx) => <ContentItem content={c} key={idx} />)}
            </ContentChildren>
        }

        if (content.type && content.type == 'mp4')
            return <video key={content.path} src={content.path}></video>

        if (content.type && content.type == 'pdf')
            return <PDF key={content.path} url={content.path} />

        if (content.type && content.type == 'iframe')
            return <iframe key={content.path} src={content.path}></iframe>

        if (!content.type)
            return <div dangerouslySetInnerHTML={{__html: content.content || content}} />

        return <div>No content</div>
    }
}

class Content extends React.PureComponent {
    render() {
        const {content} = this.props;

        return <div className="content">
                <ContentItem content={content} />
        </div>
    }
}

class UI extends React.Component {
    state = {
        active: 'Introduction',
    }

    componentDidUpdate() {
        const {active} = this.state;

        // Update URL
        history.replaceState({}, active || 'HTML Tour', active == null ? '' : `#${slug(active)}`);
    }

    render() {
        const {content} = this.props
        const {active} = this.state

        const activeParts = active.split('/')
        let activeContent = content.get(activeParts[0])

        if (activeParts.length > 1) {
            activeContent = activeContent.find(c => c.name == activeParts[1])
        }

        return <div className="tour">
            <Menu>
                {content.entrySeq().map(([key, value]) =>
                    <Link active={active == key} key={key} name={key} children={value} onClick={active =>
                        this.setState({active})
                    } />)}
            </Menu>
            <Content content={activeContent} name={active} />
        </div>
    }
}

document.addEventListener('DOMContentLoaded', e => {
    const mount = document.createElement('div')
    render(<UI content={OrderedMap(content)} />, mount)
    document.querySelector('body').appendChild(mount)
})