import {ForageWikiProps} from "./ForageWikiProps.ts"
import {ForageWikiItem} from "../../types/ForageWikiItem.ts";
import ExpandableCard from "../../components/content/expandableCard/ExpandableCard.tsx";
import {Container} from "react-bootstrap";

export default function ForageWiki(props: Readonly<ForageWikiProps>) {

	return (
		<Container fluid>
			<h2>Forage Wiki</h2>
			{props.forageWikiItems.length > 0 ?
				<>
					{props.forageWikiItems.map((forageWikiItem:ForageWikiItem) => (
							<div key={forageWikiItem.id}>
								<ExpandableCard
									forageWikiItem={forageWikiItem}
									fetchWikiData={props.fetchWikiData}/>
							</div>
						))}
				</>
				:
				<p>no Forage wiki items found</p>
			}
		</Container>
	)
}