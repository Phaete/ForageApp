import {ForageWikiItemEditorProps} from "./ForageWikiItemEditorProps.ts"
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {useEffect, useState} from "react";
import axios from "axios";

export default function ForageWikiItemEditor(props: Readonly<ForageWikiItemEditorProps>) {

    const emptyForageWikiItem: ForageWikiItem = {
        id: crypto.randomUUID().toString(),
        name: "Create new ForageWikiItem",
        category: "",
        source: "",
        description: "",
        season: "",
        imageURLs: []
    }

    const [addForageWikiItem, setAddForageWikiItem] = useState<boolean>(false)
    const [newForageWikiItem, setNewForageWikiItem] = useState<ForageWikiItem>(emptyForageWikiItem)
    const [imageUrl, setImageUrl] = useState<string>("")

    function addNewForageWikiItem() {
        setAddForageWikiItem(false)
        axios.post("api/forageWikiItems", newForageWikiItem)
            .then(() => props.fetchWikiData())
            .catch(error => console.log(error))
        resetForm()
    }

    function updateForageWikiItem() {
        if (props.forageWikiItemToEdit && props.setEditItem) {
            setAddForageWikiItem(false)
            axios.put("api/forageWikiItems/" + props.forageWikiItemToEdit.id, newForageWikiItem)
                .then(() => props.fetchWikiData())
                .catch(error => console.log(error))
            props.setEditItem(false)
        } else {
            alert("No forage wiki item to update")
        }
    }

    const [formError, setFormError] = useState<boolean>(false)

    function validateForm() {
        if (
            newForageWikiItem.name === "" ||
            newForageWikiItem.name === "Create new ForageWikiItem" ||
            newForageWikiItem.category === "" ||
            newForageWikiItem.source === "" ||
            newForageWikiItem.description === "" ||
            newForageWikiItem.season === ""
        ) {
            setFormError(true);
        } else {
            setFormError(false);
            if (props.forageWikiItemToEdit){
                updateForageWikiItem();
            } else {
                setNewForageWikiItem({
                    ...newForageWikiItem,
                    imageURLs: [...newForageWikiItem.imageURLs, imageUrl]
                })
                addNewForageWikiItem();
            }
        }
    }

    function resetForm() {
        if (props.forageWikiItemToEdit && props.setEditItem) {
            setNewForageWikiItem(emptyForageWikiItem)
            props.setEditItem(false)
        } else {
            setAddForageWikiItem(false)
            setNewForageWikiItem(emptyForageWikiItem)
        }
    }

    useEffect(() => {
        if (props.forageWikiItemToEdit) {
            setNewForageWikiItem(props.forageWikiItemToEdit)
        } else {
            setNewForageWikiItem(emptyForageWikiItem)
        }
    }, []);

	return (
		<div className={"p-5"}>
            {props.forageWikiItemToEdit ?
                <div>
                    <div className={"flex flex-col flex-stretch"}>
                        {formError ?
                            <p style={{color: "red", textAlign: "center"}}>Only the image url field is optional, please
                                make sure everything else is filled out</p> : null}
                        <input
                            type="text"
                            defaultValue={props.forageWikiItemToEdit.name}
                            onChange={event => setNewForageWikiItem(
                                {...newForageWikiItem, name: event.target.value}
                            )}
                        />
                        <select
                            defaultValue={props.forageWikiItemToEdit.category}
                            onChange={event => setNewForageWikiItem(
                                {...newForageWikiItem, category: event.target.value}
                            )}
                        >
                            <option value="FRUIT">Fruit</option>
                            <option value="NUT">Nut</option>
                            <option value="MUSHROOM">Mushroom</option>
                            <option value="HERB">Herb</option>
                            <option value="FLOWER">Flower</option>
                            <option value="VEGETABLE">Vegetable</option>
                            <option value="WILD_GREEN">Wild Green</option>
                            <option value="ROOTS_TUBERS">Roots and Tubers</option>
                            <option value="OTHER">Other</option>
                        </select>
                        <select
                            defaultValue={props.forageWikiItemToEdit.source}
                            onChange={event => setNewForageWikiItem({...newForageWikiItem, source: event.target.value})}
                        >
                            <option value="TREE">Tree</option>
                            <option value={"BUSH"}>Bush</option>
                            <option value={"VINE"}>Vine</option>
                            <option value={"GROUND_PLANT"}>Ground Plant</option>
                            <option value={"OTHER"}>Other</option>
                        </select>
                        <input
                            type="text"
                            defaultValue={props.forageWikiItemToEdit.description}
                            onChange={event => setNewForageWikiItem({
                                ...newForageWikiItem,
                                description: event.target.value
                            })}
                        />
                        <select
                            defaultValue={props.forageWikiItemToEdit.season}
                            onChange={event => setNewForageWikiItem({...newForageWikiItem, season: event.target.value})}
                        >
                            <option value={"SPRING"}>Spring</option>
                            <option value={"SUMMER"}>Summer</option>
                            <option value={"FALL"}>Fall</option>
                            <option value={"WINTER"}>Winter</option>
                        </select>
                        <input
                            type="text"
                            defaultValue={props.forageWikiItemToEdit.imageURLs.toString()}
                        />
                        <div className={"flex flex-row justify-center"}>
                            <button type={"button"} onClick={validateForm}>Save</button>
                            <button type={"button"} onClick={resetForm}>Cancel</button>
                        </div>
                    </div>
                </div>
                : (
                    <div>
                        {addForageWikiItem ?
                            <div className={"flex flex-col flex-stretch"}>
                                {formError ?
                                    <p style={{color: "red", textAlign: "center"}}>Only the image url field is optional,
                                        please make sure everything else is filled out</p> : null}
                                <input
                                    type="text"
                                    placeholder="Name"
                                    onChange={event => setNewForageWikiItem({
                                        ...newForageWikiItem,
                                        name: event.target.value
                                    })}
                                />
                                <select
                                    onChange={event => setNewForageWikiItem({
                                        ...newForageWikiItem,
                                        category: event.target.value
                                    })}
                                >
                                    <option value={""} selected disabled={true}>Select Category</option>
                                    <option value="FRUIT">Fruit</option>
                                    <option value="NUT">Nut</option>
                                    <option value="MUSHROOM">Mushroom</option>
                                    <option value="HERB">Herb</option>
                                    <option value="FLOWER">Flower</option>
                                    <option value="VEGETABLE">Vegetable</option>
                                    <option value="WILD_GREEN">Wild Green</option>
                                    <option value="ROOTS_TUBERS">Roots and Tubers</option>
                                    <option value="OTHER">Other</option>
                                </select>
                                <select
                                    onChange={event => setNewForageWikiItem({
                                        ...newForageWikiItem,
                                        source: event.target.value
                                    })}
                                >
                                    <option value={""} selected disabled={true}>Select Source</option>
                                    <option value="TREE">Tree</option>
                                    <option value={"BUSH"}>Bush</option>
                                    <option value={"VINE"}>Vine</option>
                                    <option value={"GROUND_PLANT"}>Ground Plant</option>
                                    <option value={"OTHER"}>Other</option>
                                </select>
                                <input
                                    type="text"
                                    placeholder="Description"
                                    onChange={event => setNewForageWikiItem({
                                        ...newForageWikiItem,
                                        description: event.target.value
                                    })}
                                />
                                <select
                                    onChange={event => setNewForageWikiItem({
                                        ...newForageWikiItem,
                                        season: event.target.value
                                    })}
                                >
                                    <option value={""} selected disabled={true}>Select Season</option>
                                    <option value={"SPRING"}>Spring</option>
                                    <option value={"SUMMER"}>Summer</option>
                                    <option value={"FALL"}>Fall</option>
                                    <option value={"WINTER"}>Winter</option>
                                </select>
                                <input
                                    type="text"
                                    placeholder="Image URL"
                                    onChange={event => setImageUrl(event.target.value)}
                                />
                                <div className={"flex flex-row justify-center"}>
                                    <button type={"button"} onClick={validateForm}>Add</button>
                                    <button type={"button"} onClick={resetForm}>Cancel</button>
                                </div>
                            </div>
                        :
                            <button onClick={() => setAddForageWikiItem(true)}>Add ForageWikiItem</button>}
                    </div>
                )
            }
        </div>
    )
}