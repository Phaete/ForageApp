import {ForageMapItem} from "../../../types/ForageMapItem.ts";

export type ForageMapItemViewProps = {
	forageMapItem: ForageMapItem
	setIsEditable: (isEditable: boolean) => void
	fetchForageMapItems: () => void
	setDetailedForageMapItem: (detailedForageMapItem: ForageMapItem | null) => void
}