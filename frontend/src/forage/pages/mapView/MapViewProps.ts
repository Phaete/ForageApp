import {ForageWikiItem} from "../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../types/CustomMarker.ts";
import {ForageMapItem} from "../../types/ForageMapItem.ts";

export type MapViewProps = {
	forageWikiItems: ForageWikiItem[]
	customMarker: CustomMarker[]
	forageMapItems: ForageMapItem[]
	fetchForageMapItems: () => void
}