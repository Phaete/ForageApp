import {AdminDashboardProps} from "./AdminDashboardProps.ts"
import ForageWiki from "../forageWiki/ForageWiki.tsx";
import CustomMarkerGallery from "../customMarkerGallery/CustomMarkerGallery.tsx";
import ForageWikiItemEditor from "../forageWiki/forageWikiItemEditor/ForageWikiItemEditor.tsx";


export default function AdminDashboard(props: Readonly<AdminDashboardProps>) {


    return (
        <div className={"boxed"}>
            <h1>Admin Dashboard</h1>
            <ForageWiki forageWikiItems={props.forageWikiItems} fetchWikiData={props.fetchWikiData}/>
            <h2>Add ForageWikiItem</h2>
			<ForageWikiItemEditor fetchWikiData={props.fetchWikiData}/>

            <CustomMarkerGallery customMarkers={props.customMarkers} fetchCustomMarkerData={props.fetchCustomMarkerData}/>
        </div>
    )
}