import './Button.css'

export default function Button({children, onClick}) {

    return (
        <button className='button active' onClick={onClick}>{children}</button>
    )
}