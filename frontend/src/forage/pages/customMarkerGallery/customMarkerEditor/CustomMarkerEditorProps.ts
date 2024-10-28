import {CustomMarker} from "../../../types/CustomMarker.ts";

export type CustomMarkerEditorProps = {
	fetchCustomMarkerData: () => void,
	customMarkerToEdit?: CustomMarker,
	setEditItem?: (editItem: boolean) => void
}