/*
 * Copyright 2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.binder.reactivestreams.factory;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.DirectProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import org.springframework.cloud.stream.reactive.FluxSender;

/**
 * @author Marius Bogoevici
 */
public class FluxSenderPublisher<T> implements FluxSender, Publisher<T> {

	private DirectProcessor<T> internalFlux = DirectProcessor.create();

	@Override
	public Mono<Void> send(Flux<?> flux) {
		return flux.doOnNext(t -> internalFlux.onNext((T) t)).then();
	}

	public Flux<T> getInternalFlux() {
		return internalFlux;
	}

	@Override
	public void subscribe(Subscriber<? super T> subscriber) {
		internalFlux.subscribe(subscriber);
	}
}
