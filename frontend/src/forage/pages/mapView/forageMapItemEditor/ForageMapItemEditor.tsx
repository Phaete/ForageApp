import {ForageMapItemEditorProps} from "./ForageMapItemEditorProps.ts"
import {useState} from "react";
import {ForageMapItem} from "../../../types/ForageMapItem.ts";
import axios from "axios";

export default function ForageMapItemEditor(props: Readonly<ForageMapItemEditorProps>) {

    const emptyForageMapItem: ForageMapItem = {
        id: crypto.randomUUID().toString(),
        forageWikiItem: {
            id: "",
            name: "",
            category: "",
            source: "",
            description: "",
            season: "",
            imageURLs: []
        },
        customMarker: {
            id: "",
            name: "",
            iconUrl: "",
            iconSize: [0, 0],
            iconAnchor: [0, 0],
            popupAnchor: [0, 0]
        },
        position: props.forageMapItemPosition,
        assessment: {
            quality: "",
            quantity: "",
        },
        notes: "",
    }

    const [forageMapItemToAdd, setForageMapItemToAdd] = useState<ForageMapItem>(props.forageMapItemToEdit || emptyForageMapItem)

    function handleSubmit() {
        if (props.forageMapItemToEdit) {
            axios.put("api/forageMapItems/" + props.forageMapItemToEdit.id, forageMapItemToAdd)
                .then(() => {
                    props.fetchForageMapItems()
                    props.setDetailedForageMapItem(forageMapItemToAdd)
                })
                .catch(error => console.log(error))
        } else {
            axios.post("api/forageMapItems", forageMapItemToAdd)
                .then(() => props.fetchForageMapItems())
                .catch(error => console.log(error))
            setForageMapItemToAdd(emptyForageMapItem)
        }
        props.setAddForageMapItem(false)
    }

    function resetForm() {
        setForageMapItemToAdd(emptyForageMapItem)
        props.setAddForageMapItem(false)
    }

	return (
        <div>
            <div className={"flex flex-col flex-stretch"}>
                <select onChange={
                    event => {
                        setForageMapItemToAdd(
                            {
                                ...forageMapItemToAdd,
                                forageWikiItem: props.forageWikiItems.find(
                                    forageWikiItem => forageWikiItem.id === event.target.value
                                ) ?? {
                                    id: "",
                                    name: "",
                                    category: "",
                                    source: "",
                                    description: "",
                                    season: "",
                                    imageURLs: []
                                }
                            }
                        )
                    }
                } value={forageMapItemToAdd.forageWikiItem.id}>
                    <option value={""} selected disabled={true}>Please choose a forage item.</option>
                    {
                        props.forageWikiItems.map((forageWikiItem) => {
                            return <option key={forageWikiItem.id}
                                           value={forageWikiItem.id}>{forageWikiItem.name}</option>
                        })
                    }
                </select>
                <select onChange={
                    event => {
                        setForageMapItemToAdd(
                            {
                                ...forageMapItemToAdd,
                                customMarker: props.customMarker.find(
                                    customMarker => customMarker.id === event.target.value
                                ) ?? {
                                    id: "",
                                    name: "",
                                    iconUrl: "",
                                    iconSize: [],
                                    iconAnchor: [],
                                    popupAnchor: []
                                }
                            }
                        )
                    }
                } value={forageMapItemToAdd.customMarker.id}>
                    <option value={""} disabled={true}>Please choose a marker.</option>
                    {
                        props.customMarker.map((customMarker) => {
                            return <option key={customMarker.id}
                                           value={customMarker.id}>{customMarker.name}</option>
                        })
                    }
                </select>
                <select onChange={
                    event => setForageMapItemToAdd(
                        {
                            ...forageMapItemToAdd,
                            assessment: {
                                quality: forageMapItemToAdd.assessment.quality,
                                quantity: event.target.value
                            }
                        }
                    )
                } value={forageMapItemToAdd.assessment.quantity}>
                    <option value={""} selected disabled={true}>Please choose a quantity.</option>
                    <option value={"NONE"}>None</option>
                    <option value={"LOW"}>Low</option>
                    <option value={"MEDIUM"}>Medium</option>
                    <option value={"HIGH"}>High</option>
                    <option value={"ABUNDANT"}>Abundant</option>
                </select>
                <select onChange={
                    event => setForageMapItemToAdd(
                        {
                            ...forageMapItemToAdd,
                            assessment: {
                                quality: event.target.value,
                                quantity: forageMapItemToAdd.assessment.quantity
                            }
                        }
                    )
                } value={forageMapItemToAdd.assessment.quality}>
                    <option value={""} disabled={true}>Please choose a quality.</option>
                    <option value={"POOR"}>Poor</option>
                    <option value={"FAIR"}>Fair</option>
                    <option value={"GOOD"}>Good</option>
                    <option value={"EXCELLENT"}>Excellent</option>
                </select>
                <input type={"text"} placeholder={"Notes"} onChange={
                    event => setForageMapItemToAdd(
                        {...forageMapItemToAdd, notes: event.target.value}
                    )
                } value={forageMapItemToAdd.notes}/>
                <div className={"flex flex-row"}>
                    <span>
                        <button type={"button"} onClick={() => {
                            handleSubmit()
                        }}>Submit
                        </button>
                    </span>
                    <span>
                        <button type={"button"} onClick={() => {
                            resetForm()
                        }}>Cancel
                        </button>
                    </span>
                </div>
            </div>
        </div>
    )
}