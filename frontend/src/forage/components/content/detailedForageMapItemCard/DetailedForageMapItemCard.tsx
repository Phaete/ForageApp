import {DetailedForageMapItemCardProps} from "./DetailedForageMapItemCardProps.ts"
import ForageMapItemEditor from "../../../pages/mapView/forageMapItemEditor/ForageMapItemEditor.tsx";
import ForageMapItemView from "../forageMapItemView/ForageMapItemView.tsx";
import {useState} from "react";
import {Offcanvas} from "react-bootstrap";


export default function DetailedForageMapItemCard(props: Readonly<DetailedForageMapItemCardProps>) {
	const [isEditorOpen, setIsEditorOpen] = useState<boolean>(false)

	function handleClose() {
		props.setShowDetailedForageMapItemDrawer(false)
		props.setDetailedForageMapItem(null)
	}

	return (
		<Offcanvas
			show={props.showDetailedForageMapItemDrawer}
			onHide={handleClose}
			placement={"bottom"}>
			<Offcanvas.Body>
				{isEditorOpen ?
					<ForageMapItemEditor
						forageMapItemPosition={props.forageMapItem.position}
						fetchForageMapItems={props.fetchForageMapItems}
						forageWikiItems={props.forageWikiItems}
						customMarker={props.customMarker}
						setAddForageMapItem={setIsEditorOpen}
						forageMapItemToEdit={props.forageMapItem}
						setDetailedForageMapItem={props.setDetailedForageMapItem}
						user={props.user}/>
					:
					<ForageMapItemView
						forageMapItem={props.forageMapItem}
						setIsEditable={setIsEditorOpen}
						fetchForageMapItems={props.fetchForageMapItems}
						setDetailedForageMapItem={props.setDetailedForageMapItem}/>
				}
			</Offcanvas.Body>
		</Offcanvas>
	)
}