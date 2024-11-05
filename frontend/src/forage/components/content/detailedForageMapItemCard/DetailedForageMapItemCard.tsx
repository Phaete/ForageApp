import {DetailedForageMapItemCardProps} from "./DetailedForageMapItemCardProps.ts"
import ForageMapItemEditor from "../../../pages/mapView/forageMapItemEditor/ForageMapItemEditor.tsx";
import ForageMapItemView from "../forageMapItemView/ForageMapItemView.tsx";
import {useState} from "react";


export default function DetailedForageMapItemCard(props: Readonly<DetailedForageMapItemCardProps>) {
	const [isEditorOpen, setIsEditorOpen] = useState<boolean>(false)

	return (
		<>
			{isEditorOpen ?
				<ForageMapItemEditor
					forageMapItemPosition={props.forageMapItem.position}
					fetchForageMapItems={props.fetchForageMapItems}
					forageWikiItems={props.forageWikiItems}
					customMarker={props.customMarker}
					setAddForageMapItem={setIsEditorOpen}
					forageMapItemToEdit={props.forageMapItem}
					setDetailedForageMapItem={props.setDetailedForageMapItem}/>
				:
				<ForageMapItemView
					forageMapItem={props.forageMapItem}
					setIsEditable={setIsEditorOpen}
					fetchForageMapItems={props.fetchForageMapItems}
					setDetailedForageMapItem={props.setDetailedForageMapItem}/>
			}
		</>
	)
}