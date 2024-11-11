import {ForageMapItemViewProps} from "./ForageMapItemViewProps.ts"
import axios from "axios";
import {Card} from "react-bootstrap";

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
        <Card className={"w-100"}>
            <Card.Header className={"flex flex-row justify-between align-center"}>
                <span>
                    <h3>{props.forageMapItem.forageWikiItem.name} <span
                        className={"font-small"}>@ {props.forageMapItem.position.latitude.toFixed(4)}, {props.forageMapItem.position.longitude.toFixed(4)}</span></h3>
                </span>
                <span>
                    <button type={"button"}
                            onClick={() => props.setDetailedForageMapItem(null)}>Clear Selection</button>
                </span>
            </Card.Header>
            <span>
                <span>
                    Quality: {props.forageMapItem.assessment.quality}
                </span>
                <span className={"mx-2"}>|</span>
                <span>
                    Quantity: {props.forageMapItem.assessment.quantity}
                </span>
            </span>
            <span>
                <span>
                    Category: {props.forageMapItem.forageWikiItem.category}
                </span>
                <span className={"mx-2"}>|</span>
                <span>
                    Source: {props.forageMapItem.forageWikiItem.source}
                </span>
            </span>
            <span>
                <span>
                    Season: {props.forageMapItem.forageWikiItem.season}
                </span>
                <span className={"mx-2"}>|</span>
                <span>
                    Additional Notes: {props.forageMapItem.notes}
                </span>
            </span>
            <p>
                Description: <br/>{props.forageMapItem.forageWikiItem.description}
            </p>
            <div className={"flex flex-row-reverse justify-center"}>
                <button type={"button"} onClick={() => handleDelete()}>
                    Delete
                </button>
                <button type={"button"} onClick={() => props.setIsEditable(true)}>
                    Edit
                </button>
            </div>
        </Card>
    )
}