package com.startrip.codebase.curation.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.startrip.codebase.curation.domain.CurationChain;

import com.startrip.codebase.curation.domain.CurationInputObject;
import com.startrip.codebase.curation.domain.chains.ChainType;
import com.startrip.codebase.curation.dto.ResponseLocationDto;
import com.startrip.codebase.trip.domain.place.domain.place.Place;
import com.startrip.codebase.domain.place.QPlace;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import static com.querydsl.core.types.dsl.MathExpressions.*;

@Slf4j
@Service
public class LocationFilterService implements CurationChain<CurationInputObject, CurationInputObject> {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;

    Double [] userInputLocate = new Double [2];

    @Override
    public CurationInputObject process(CurationInputObject input) {


        Object object = input.getData(ChainType.LOCATION);


        if (object instanceof Double []){
            userInputLocate= (Double []) object;
            NumberExpression<Double> distanceFormula = getDistanceFormula(userInputLocate[0], userInputLocate[1]);
            input.getBooleanBuilder().and(distanceFormula.lt(5));
        }
        return input;
    }

    public List<ResponseLocationDto> getPlaceList(Double [] input) {
        List<ResponseLocationDto> responsePlaceList= new ArrayList<>();

        NumberExpression<Double> distanceFormula = getDistanceFormula(input[0], input[1]);
        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(distanceFormula.lt(5));
        JPAQuery<Place> query = new JPAQuery<>();

        QPlace qPlace = QPlace.place;
        List<Place> placeList =
                jpaQueryFactory.select(qPlace)
                        .from(qPlace)
                        .where(whereClause)
                        .fetch();

        for (Place place : placeList) {
            Double [] currentPlaceLocation = new Double [2];
            currentPlaceLocation[0] = place.getLatitude();
            currentPlaceLocation[1] = place.getLongitude();

            Double distance = getDistance(input, currentPlaceLocation);
            //log.info("계산된 거리:" + distance);

            ResponseLocationDto dto = new ResponseLocationDto();
            dto.setPlaceName(place.getPlaceName());
            dto.setLatitude(place.getLatitude());
            dto.setLongitude(place.getLongitude());
            dto.setPlaceAddress(place.getAddress());
            dto.setDistance(distance);

            responsePlaceList.add(dto);
        }

        return responsePlaceList;
    }

    public Double getDistance(Double [] input, Double [] current){
        double distance;
        double radius = 6371; // 지구 반지름(km)
        double toRadian = Math.PI / 180;

        double deltaLatitude = Math.abs(input[0] - current[0]) * toRadian;
        double deltaLongitude = Math.abs(input[1] - current[1]) * toRadian;

        double sinDeltaLat = Math.sin(deltaLatitude / 2);
        double sinDeltaLng = Math.sin(deltaLongitude / 2);
        double squareRoot = Math.sqrt(
                sinDeltaLat * sinDeltaLat +
                        Math.cos(input[0] * toRadian) * Math.cos(current[0] * toRadian) * sinDeltaLng * sinDeltaLng);

        distance = 2 * radius * Math.asin(squareRoot);

        return distance;
    }


    public NumberExpression<Double> getDistanceFormula(Double inputLatitude, Double inputLongitude) {

        QPlace qPlace = QPlace.place;
        NumberPath<Double> lat = qPlace.latitude;
        NumberPath<Double> lng = qPlace.longitude;

        NumberExpression<Double> formula = (acos
                (cos(radians(lat))
                        .multiply(cos(radians(Expressions.constant(inputLatitude))))
                        .multiply(cos(radians(Expressions.constant(inputLongitude))
                                        .subtract(radians(lng))
                                )
                        )
                        .add(sin(radians(lat))
                                .multiply(sin(radians(Expressions.constant(inputLatitude))))
                        )
                )
        ).multiply(Expressions.constant(6371));

        return formula;
    }
}


