import {CustomMarker} from "../../types/CustomMarker.ts";

export type CustomMarkerGalleryProps = {
	customMarkers: CustomMarker[],
	fetchCustomMarkerData: () => void,
	setEditItem?: (editItem: boolean) => void
}