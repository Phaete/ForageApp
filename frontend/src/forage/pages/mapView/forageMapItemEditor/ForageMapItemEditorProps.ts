import {GeoPosition} from "../../../types/GeoPosition.ts";
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";

export type ForageMapItemEditorProps = {
	forageMapItemPosition: GeoPosition,
	fetchForageMapItems: () => void,
	forageWikiItems: ForageWikiItem[],
	customMarker: CustomMarker[]
	setAddForageMapItem: (addForageMapItem: boolean) => void
}