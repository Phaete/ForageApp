import {ForageMapItem} from "../../../types/ForageMapItem.ts";
import {ForageWikiItem} from "../../../types/ForageWikiItem.ts";
import {CustomMarker} from "../../../types/CustomMarker.ts";

export type DetailedForageMapItemCardProps = {
	forageWikiItems: ForageWikiItem[],
	customMarker: CustomMarker[],
	forageMapItem: ForageMapItem,
	fetchForageMapItems: () => void,
	setDetailedForageMapItem: (detailedForageMapItem: ForageMapItem | null) => void
}