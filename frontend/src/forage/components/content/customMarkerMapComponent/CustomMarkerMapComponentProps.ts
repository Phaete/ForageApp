import {ForageMapItem} from "../../../types/ForageMapItem.ts";

export type CustomMarkerMapComponentProps = {
	forageMapItem: ForageMapItem,
	zoom: number,
	setDetailedForageMapItem: (detailedForageMapItem: ForageMapItem) => void
	setShowDetailedForageMapItemDrawer: (showDetailedForageMapItemDrawer: boolean) => void
}