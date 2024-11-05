import {ForageMapItemViewProps} from "./ForageMapItemViewProps.ts"
import axios from "axios";

export default function ForageMapItemView(props: Readonly<ForageMapItemViewProps>) {

    function handleDelete() {
        axios.delete("api/forageMapItems/" + props.forageMapItem.id)
            .then(() => {
                props.fetchForageMapItems()
                props.setDetailedForageMapItem(null)
            })
            .catch(error => console.log(error))
    }

	return (
		<div className={"w-100"}>
			<div className={"flex flex-row justify-between align-center"}>
                <span>
                    <h3>{props.forageMapItem.forageWikiItem.name}</h3>
                </span>
                <span>
                    <button type={"button"} onClick={() => props.setDetailedForageMapItem(null)}>Clear Selection</button>
                </span>
            </div>
            <p>
                Position: [{props.forageMapItem.position.latitude.toFixed(4)}, {props.forageMapItem.position.longitude.toFixed(4)}]<br/>
                Quality: {props.forageMapItem.assessment.quality} <br/>
                Quantity: {props.forageMapItem.assessment.quantity}
            </p>
            <p>
                Category: {props.forageMapItem.forageWikiItem.category}<br/>
                Source: {props.forageMapItem.forageWikiItem.source}<br/>
                Season: {props.forageMapItem.forageWikiItem.season}<br/>
                Description: {props.forageMapItem.forageWikiItem.description}
            </p>
            <div className={"flex flex-row-reverse justify-center"}>
                <button type={"button"} onClick={() => handleDelete()}>
                    Delete
                </button>
                <button type={"button"} onClick={() => props.setIsEditable(true)}>
                    Edit
                </button>
            </div>
        </div>
    )
}