import {ForageWikiItem} from "../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../types/CustomMarker.ts";

export type AdminDashboardProps = {
	forageWikiItems: ForageWikiItem[],
	customMarkers: CustomMarker[],
	fetchWikiData: () => void,
	fetchCustomMarkerData: () => void
}