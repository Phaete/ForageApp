import {CustomMarkerEditorProps} from "./CustomMarkerEditorProps.ts"
import {CustomMarker} from "../../../types/CustomMarker.ts";
import {useEffect, useState} from "react";
import axios from "axios";

export default function CustomMarkerEditor(props: Readonly<CustomMarkerEditorProps>) {

    const emptyCustomMarker: CustomMarker = {
        id: crypto.randomUUID().toString(),
        name: "Create new Marker",
        iconUrl: "",
        iconSize: [],
        iconAnchor: [],
        popupAnchor: []
    }

    const [addCustomMarker, setAddCustomMarker] = useState<boolean>(false)
    const [newCustomMarker, setNewCustomMarker] = useState<CustomMarker>(emptyCustomMarker)


    function addNewCustomMarker() {
        setAddCustomMarker(false)
        axios.post("api/customMarkers", newCustomMarker)
            .then(() => props.fetchCustomMarkerData())
            .catch(error => console.log(error))
    }

    function updateCustomMarker() {
        if (props.customMarkerToEdit && props.setEditItem) {
            setAddCustomMarker(false)
            axios.put("api/customMarkers/" + props.customMarkerToEdit.id, newCustomMarker)
                .then(() => props.fetchCustomMarkerData())
                .catch(error => console.log(error))
            props.setEditItem(false)
        } else {
            alert("No custom marker to update")
        }
    }

    const [formError, setFormError] = useState<boolean>(false)

    function validateForm() {
        if (
            newCustomMarker.name === "" ||
            newCustomMarker.iconUrl === "" ||
            !newCustomMarker.iconSize ||
            !newCustomMarker.iconAnchor ||
            !newCustomMarker.popupAnchor
        ) {
            setFormError(true)
        } else {
            setFormError(false)
            if (addCustomMarker) {
                addNewCustomMarker()
            } else {
                updateCustomMarker()
            }
        }
    }

    function resetForm() {
        if (props.customMarkerToEdit && props.setEditItem) {
            setNewCustomMarker(emptyCustomMarker)
            props.setEditItem(false)
        } else {
            setAddCustomMarker(false)
            setNewCustomMarker(emptyCustomMarker)
        }
    }

    useEffect(() => {
        if (props.customMarkerToEdit) {
            setNewCustomMarker(props.customMarkerToEdit)
        } else {
            setNewCustomMarker(emptyCustomMarker)
        }
    }, []);

    return (
		<div className={"p-5"}>
            {props.customMarkerToEdit ?
                <div>
                    <div className={"flex flex-col flex-stretch"}>
                        <input
                            type={"text"}
                            defaultValue={props.customMarkerToEdit.name}
                            onChange={event => setNewCustomMarker(
                                {...newCustomMarker, name: event.target.value}
                            )}
                        />
                        <input
                            type={"text"}
                            defaultValue={props.customMarkerToEdit.iconUrl}
                            onChange={event => setNewCustomMarker(
                                {...newCustomMarker, iconUrl: event.target.value}
                            )}
                        />
                        <span>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.iconSize[0]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        iconSize: [Number(event.target.value), newCustomMarker.iconSize[1]]
                                    }
                                )
                                }/>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.iconSize[1]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        iconSize: [newCustomMarker.iconSize[0], Number(event.target.value)]
                                    }
                                )
                                }/>
                        </span>
                        <span>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.iconAnchor[0]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        iconAnchor: [Number(event.target.value), newCustomMarker.iconAnchor[1]]
                                    }
                                )
                                }/>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.iconAnchor[1]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        iconAnchor: [newCustomMarker.iconAnchor[0], Number(event.target.value)]
                                    }
                                )
                                }/>
                        </span>
                        <span>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.popupAnchor[0]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        popupAnchor: [Number(event.target.value), newCustomMarker.popupAnchor[1]]
                                    }
                                )
                                }/>
                            <input
                                type={"number"}
                                defaultValue={props.customMarkerToEdit.popupAnchor[1]}
                                onChange={event => setNewCustomMarker(
                                    {
                                        ...newCustomMarker,
                                        popupAnchor: [newCustomMarker.popupAnchor[0], Number(event.target.value)]
                                    }
                                )
                                }/>
                        </span>
                        <div className={"flex flex-row justify-center"}>
                            <button type={"button"} onClick={validateForm}>Save</button>
                            <button type={"button"} onClick={resetForm}>Cancel</button>
                        </div>
                    </div>
                </div>
                : (
                    <div>
                        {addCustomMarker ?
                            <div className={"flex flex-col flex-stretch"}>
                                {formError ?
                                    <p style={{color: "red", textAlign: "center"}}>Only the image url field is optional,
                                        please make sure everything else is filled out</p> : null}
                                <input type={"text"} placeholder={"Name"} onChange={
                                    (event) => setNewCustomMarker(
                                        {...newCustomMarker, name: event.target.value}
                                    )
                                }/>
                                <input type={"text"} placeholder={"Icon Url"} onChange={
                                    (event) => setNewCustomMarker(
                                        {...newCustomMarker, iconUrl: event.target.value}
                                    )
                                }/>
                                <span>
                                    <input type={"number"} placeholder={"Icon Size X"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                iconSize: [Number(event.target.value), newCustomMarker.iconSize[1]]
                                            }
                                        )
                                    }/>
                                    <input type={"number"} placeholder={"Icon Size Y"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                iconSize: [newCustomMarker.iconSize[0], Number(event.target.value)]
                                            }
                                        )
                                    }/>
                                </span>
                                <span>
                                    <input type={"number"} placeholder={"Icon Anchor X"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                iconAnchor: [Number(event.target.value), newCustomMarker.iconAnchor[1]]
                                            }
                                        )
                                    }/>
                                    <input type={"number"} placeholder={"Icon Anchor Y"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                iconAnchor: [newCustomMarker.iconAnchor[0], Number(event.target.value)]
                                            }
                                        )
                                    }/>
                                </span>
                                <span>
                                    <input type={"number"} placeholder={"Popup Anchor X"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                popupAnchor: [Number(event.target.value), newCustomMarker.popupAnchor[1]]
                                            }
                                        )
                                    }/>
                                    <input type={"number"} placeholder={"Popup Anchor Y"} onChange={
                                        (event) => setNewCustomMarker(
                                            {
                                                ...newCustomMarker,
                                                popupAnchor: [newCustomMarker.popupAnchor[0], Number(event.target.value)]
                                            }
                                        )
                                    }/>
                                </span>
                                <div className={"flex flex-row justify-center"}>
                                    <button type={"button"} onClick={addNewCustomMarker}>Add</button>
                                    <button type={"button"} onClick={resetForm}>Cancel</button>
                                </div>
                            </div>
                        :
                            <button onClick={() => setAddCustomMarker(true)}>Add Custom Marker</button>}
                    </div>
                )
            }
        </div>
    )
}