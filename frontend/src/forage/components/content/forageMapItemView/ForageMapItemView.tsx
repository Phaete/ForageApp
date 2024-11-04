import {ForageMapItemViewProps} from "./ForageMapItemViewProps.ts"
import axios from "axios";

export default function ForageMapItemView(props: Readonly<ForageMapItemViewProps>) {

    function handleDelete() {
        axios.delete("api/forageMapItems/" + props.forageMapItem.id)
            .then(() => props.fetchForageMapItems())
            .catch(error => console.log(error))
    }

	return (
		<div className={"w-50"}>
			<h3>{props.forageMapItem.forageWikiItem.name}</h3>
            <p>Position: [{props.forageMapItem.position.latitude.toFixed(4)}, {props.forageMapItem.position.longitude.toFixed(4)}]</p>
            <p>Category: {props.forageMapItem.forageWikiItem.category}</p>
            <p>Source: {props.forageMapItem.forageWikiItem.source}</p>
            <p>Season: {props.forageMapItem.forageWikiItem.season}</p>
            <p>Description: {props.forageMapItem.forageWikiItem.description}</p>
            <div className={"flex flex-row-reverse"}>
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