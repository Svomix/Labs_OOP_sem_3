import {useState} from "react";
//import './FirstPage.css'
import IntroSection from "./IntroSection.jsx";
import TabsSection from "./TabsSection.jsx";
import MainSection from "./MainSection.jsx";
import FeedbackSection from "./FeedbackSection.jsx";

export default function FirstPage({onDataChange, closeModal}) {
    const [tab, setTab] = useState('main')

    return (
        <div>
            <main>
                <IntroSection/>
                <TabsSection active={tab} onChange={(current) => setTab(current)}/>
                {tab === 'main' && (
                    <>
                        <MainSection onDataChange={onDataChange} closeModal={closeModal}/>
                    </>
                )}
                {tab === 'feedback' && (
                    <>
                        <FeedbackSection onDataChange={onDataChange} closeModal={closeModal}></FeedbackSection>
                    </>
                )}
            </main>
        </div>
    )
}