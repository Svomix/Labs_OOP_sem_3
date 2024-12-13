import {useState} from "react";
import './FirstPage.css'
import IntroSection from "./IntroSection.jsx";
import TabsSection from "./TabsSection.jsx";
import MainSection from "./MainSection.jsx";
import FeedbackSection from "./FeedbackSection.jsx";

export default function FirstPage() {
    const [tab, setTab] = useState('main')

    return (
        <div>
            <main>
                <IntroSection/>
                <TabsSection active={tab} onChange={(current) => setTab(current)}/>
                {tab === 'main' && (
                    <>
                        <MainSection/>
                    </>
                )}
                {tab === 'feedback' && (
                    <>
                        <FeedbackSection></FeedbackSection>
                    </>
                )}
            </main>
        </div>
    )
}