import {AdminDashboardProps} from "./AdminDashboardProps.ts"


export default function AdminDashboard(props: Readonly<AdminDashboardProps>) {

    return (
        <div className={"boxed"}>
            <h1>Admin Dashboard</h1>
            <h2>Forage Wiki Items</h2>
            {props.forageWikiItems.length > 0 ?
                <>
                    {props.forageWikiItems.map((forageWikiItem) => (
                        <p key={forageWikiItem.name}>{forageWikiItem.name}</p>
                    ))}
                </>
             :
                <p>No forage wiki items found</p>}

            <h2>Custom Marker</h2>
            {props.customMarker.length > 0 ?
                <>
                    {props.customMarker.map((customMarker) => (
                        <p key={customMarker.uniqueMarkerId}>{customMarker.position}</p>))}
                </>
             :
                <p>No custom marker found</p>}
        </div>
    )
}