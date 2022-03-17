package com.flair.server.document;

import com.flair.shared.grammar.GrammaticalConstruction;

public class DocumentCollectionConstructionDataFactory extends AbstractConstructionDataFactory {
	private final DocumentCollection parent;

	public DocumentCollectionConstructionDataFactory(DocumentCollection parent) {
		this.parent = parent;
	}

	@Override
	public AbstractConstructionData create(GrammaticalConstruction type) {
		return new DocumentCollectionConstructionData(type, parent);
	}
}
