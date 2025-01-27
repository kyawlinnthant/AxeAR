package com.kyawlinnthant.axear

import androidx.compose.ui.graphics.Color
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode

object Util {
    fun createMac(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        anchor: Anchor,
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val model =
            modelLoader.createInstancedModel(assetFileLocation = "mac/macmini.glb", count = 10)
                .first()
        val modelNode = ModelNode(
            modelInstance = model,
            scaleToUnits = 0.1f
        )

        val boundingBox = CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible = false
        }
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)
        return anchorNode

    }

    val axe = listOf(
        "attack.glb",
        "death.glb",
        "fidget.glb",
        "group.glb",
        "run.glb",
        "axe_walk.glb",
    )

    fun randomAxe(): String {
        val randomIndex = (0 until axe.size).random()
        return "axe/${axe[randomIndex]}"
    }

    fun createAxe(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        anchor: Anchor,
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val model =
            modelLoader.createInstancedModel(assetFileLocation = randomAxe(), count = 10).first()
        val modelNode = ModelNode(
            modelInstance = model,
            scaleToUnits = 0.1f
        )

        val boundingBox = CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible = false
        }
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)
//        listOf(modelNode, anchorNode).forEach {
//            it.onEditingChanged = { editingTransforms ->
//                boundingBox.isVisible = editingTransforms.isNotEmpty()
//            }
//        }
        return anchorNode

    }

    fun createAnchorNode(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        modelInstance: MutableList<ModelInstance>,
        anchor: Anchor,
        model: String
    ): AnchorNode {
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)
        val modelNode = ModelNode(
            modelInstance = modelInstance.apply {
                if (isEmpty()) {
                    this += modelLoader.createInstancedModel(model, 10)
                }
            }.removeAt(
                modelInstance.apply {
                    if (isEmpty()) {
                        this += modelLoader.createInstancedModel(model, 10)
                    }
                }.lastIndex
            ),

            scaleToUnits = 0.2f
        ).apply {
            isEditable = true
        }
        val boundingBox = CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible = false
        }
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)
        listOf(modelNode, anchorNode).forEach {
            it.onEditingChanged = { editingTransforms ->
                boundingBox.isVisible = editingTransforms.isNotEmpty()
            }
        }
        return anchorNode

    }
}