import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";

export type ExpandableCardProps = {
	forageWikiItem?: ForageWikiItem,
	fetchWikiData?: () => void,
	customMarker?: CustomMarker,
	fetchCustomMarkerData?: () => void,
}