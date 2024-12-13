import Button from "./components/Button/Button.jsx";

export default function TabsSection({active, onChange}) {
    return (
        <section style={{ marginBottom: '1r'}}>
            <Button isActive={active === 'main'} onClick={() => onChange('main')}>Создание функции при помощи таблицы</Button>
            <Button isActive={active === 'feedback'} onClick={() => onChange('feedback')}>Создание функции при помощи точек разбиения</Button>
        </section>
    )
}