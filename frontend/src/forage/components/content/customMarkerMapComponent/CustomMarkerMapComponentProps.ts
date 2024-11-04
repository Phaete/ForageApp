import {ForageMapItem} from "../../../types/ForageMapItem.ts";
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";

export type CustomMarkerMapComponentProps = {
	forageMapItem: ForageMapItem,
	fetchForageMapItems: () => void,
	forageWikiItems: ForageWikiItem[],
	customMarker: CustomMarker[],
	zoom: number
}